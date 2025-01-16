#!/usr/bin/env python
# coding: utf-8

# In[ ]:


#!/usr/bin/env python
# coding: utf-8

# In[ ]:


#!/usr/bin/env python
# coding: utf-8

# In[ ]:


import numpy as np
import tensorflow.compat.v1 as tf
import random
from collections import deque
from ou_noise import OUNoise
import critic_net
import actor_net
import Environment_DTW as En
import DTW_oto_sys as DS
import os 

tf.compat.v1.disable_eager_execution()

learning = 'Monitoring/learning/'
if not os.path.exists(learning):
    os.makedirs(learning)

Test = 'Monitoring/Test/'
if not os.path.exists(Test):
    os.makedirs(Test)
    
Val_set = 'Monitoring/Test/Error_val_dataset/'
if not os.path.exists(Val_set):
    os.makedirs(Val_set)    

Error_plot = 'Monitoring/learning/Error_plot_learning/'
if not os.path.exists(Error_plot):
    os.makedirs(Error_plot)

Error_file = 'Monitoring/learning/Error_dataset_learning/'
if not os.path.exists(Error_file):
    os.makedirs(Error_file)

    

environment = En.env() #call environment

_ = environment.reset() #Initialization of environment


num_train= environment.train_num()
num_high_train, max_num = environment.call_high_num()

alpha_critic = 0.9 #learning rate (based on Q)
#alpha_actor = 0.9

#Input & Output
state_nums = environment.state_num() #Q function은 (action, state) 이 두개에 의해 결정이 되므로, action까지 넣어줘야 한다.
action_nums = environment.action_num() #하나의 action 구성 list 개수를 의미한다.

input_size_critic_s = state_nums
input_size_critic_a = action_nums #일단 network는 나눠서 줄거니까.
output_size_critic = 1 
input_size_actor_s = state_nums
output_size_actor =  action_nums


#Reinforcement learning parmeter
dis = 0.99 
buffer_memory = 300000#Replay memory에 몇개를 넣을 것인가? (Buffer)
exploration_noise = OUNoise(input_size_critic_a)



def critic_train(main_critic, target_critic, main_actor, target_actor, train_batch):

    Q_old = np.zeros([1], dtype = np.float64) 
    Q_new = np.zeros([1], dtype = np.float64)
    
    x_action_stack = np.empty(0)
    x_state_stack = np.empty(0)
    y_stack = np.empty(0)
    
    x_state_stack = np.reshape(x_state_stack, (0, main_critic.input_size_critic_s))
    x_action_stack = np.reshape(x_action_stack, (0, main_critic.input_size_critic_a))
    y_stack = np.reshape(y_stack, (0, target_critic.output_size_critic)) 

    for state, action, reward, next_state, done in train_batch: 
        
        Q_old = main_critic.predict(state, action) #, main_actor
        
        next_action = target_actor.predict(next_state) #target policy로 next_state에 대한 next_action을 받아온다.

        Q_new = Q_old + alpha_critic*(reward + dis*(target_critic.predict(next_state, next_action)) - Q_old) #target
            
        y_stack = np.vstack([y_stack, Q_new])
        x_state_stack = np.vstack([x_state_stack, state]) #state를 학습시키는거지 Q를 학습시키는건 아니다.

        x_action_stack = np.vstack([x_action_stack, action])
        
        #actor에 input을 같이 줘야한다.
        loss_critic, _ = main_critic.update(x_state_stack, x_action_stack, y_stack) #, main_actor        
        

        
    return loss_critic, Q_old, Q_new


def actor_train(main_actor, target_actor, main_critic, train_batch):
    
    x_stack_actor = np.empty(0)
    
    sess = tf.Session()
    x_stack_actor = np.reshape(x_stack_actor, (0, input_size_actor_s))
    x_stack_Q = np.zeros([input_size_critic_s], dtype = np.float64)
    
    for state, action, reward, next_state, done in train_batch: 


        x_stack_actor = np.vstack([x_stack_actor, state]) 

        _ = main_actor.update(main_critic, x_stack_actor)

        
    return _   



def first_copy (sess, target_scope_name ="target", main_scope_name = "main"):

    op_holder = []
    
    main_vars = tf.get_collection(tf.GraphKeys.TRAINABLE_VARIABLES, scope=main_scope_name)
    target_vars = tf.get_collection(tf.GraphKeys.TRAINABLE_VARIABLES, scope=target_scope_name)

    for main_var, target_var in zip(main_vars, target_vars): 
        
        op_holder.append(target_var.assign(main_var.value()))

    return sess.run(op_holder)



def copy_var_ops(*, target_scope_name ="target", main_scope_name = "main"):

    op_holder = []
    tau = 0.001
    
    main_vars = tf.get_collection(tf.GraphKeys.TRAINABLE_VARIABLES, scope=main_scope_name)
    target_vars = tf.get_collection(tf.GraphKeys.TRAINABLE_VARIABLES, scope=target_scope_name)


    for main_var, target_var in zip(main_vars, target_vars): 
        
        op_holder.append(target_var.assign(tau * main_var.value() +(1 - tau)*target_var.value()))
        
        
    return op_holder



def main():
    
    divide = 100
    Q_old = np.empty(0)
    Q_new = np.empty(0)  
    st_step = 1 #action을 몇 time-step마다 취할 것인지에 대한 숫자
    state_step = 0
    Loss_step = 0
    record_frequency = 1   
    step_deadline = (num_train//divide)*divide
    validation_freq = 5000
    
    #------- High level set -------#
    buffer_h_rate = 0.15 #rate of extreme value in replay buffer
    
    '''
    Arrange of high_level_freq equation
    f = num_train/high_level_freq
    (f+1)*num_high_train/(num_train + num_high_train*f) = buffer_h_rate 
    (f+1)*num_high_train = buffer_h_rate*(num_train + num_high_train*f) 
    f*num_high_train + num_high_train = buffer_h_rate*num_train + buffer_h_rate*num_high_train*f
    f*(num_high_train - buffer_h_rate * num_high_train) = buffer_h_rate*num_train - num_high_train
    f = (buffer_h_rate*num_train - num_high_train)/(num_high_train - buffer_h_rate * num_high_train)
    num_train/high_level_freq = (buffer_h_rate*num_train - num_high_train)/(num_high_train - buffer_h_rate * num_high_train)
    high_level_freq = num_train/(buffer_h_rate*num_train - num_high_train)*(num_high_train - buffer_h_rate * num_high_train)
    high_level_freq = num_train/(buffer_h_rate*num_train - num_high_train)*(num_high_train*(1-buffer_h_rate))    
    
    '''    
    
    high_level_freq = int(num_train/(buffer_h_rate*num_train - num_high_train)*(num_high_train*(1-buffer_h_rate)))
    high_level_freq_ten = high_level_freq//divide*divide
    print("High_level info")
    print(high_level_freq)
    print(high_level_freq//divide*divide)
    print("Extreme value percentage")
    print(num_high_train/num_train*100)

    #print(high_level_freq)
    #------------------------------#
    
        
    main_update_freq = 20
    target_update_frequency = 20
    train_loop_epoch = 1
    #main이 target을 향해서 update되어가고 이후에 target_update가 이루어져야 하기때문에 main_freq < target_update가 되어야 한다.
    max_episodes = 10
    batch_size = 128 #Mini batch size Buffer에서 몇개씩 batch로 만들어서 학습시킬 것인가?
    buff_len = batch_size
    Noise_amp = max_num
    matching_feq = main_update_freq
    divide = main_update_freq
    
    # Replay buffer를 deque로 짠다. 
    buffer = deque() 
    #Memory는 50000개까지 

    reward_buffer = deque() #maxlen=100
    #reward_buffer또한 deque로 만들어서 마지막 100개까지 기억하도록 한다
    


    L_con = open(learning +"/main_condition.csv", 'w', encoding='utf-8', newline='') 
    L_con.write('"Noise_amp","batch_size","high_freq","a_learning","c_learning","Buffer_high_rate", "Extreme_value rate"\n')
    L_con.write("%f, %d, %d, %f, %f, %f, %f\n" %(Noise_amp, batch_size, high_level_freq , 0.00001, 0.0001, buffer_h_rate*100, num_high_train/num_train*100))
    L_con.close()

    with tf.Session() as sess:
     
        #formation of network for actor net
        main_actor = actor_net.actor(sess, input_size_actor_s, output_size_actor, output_size_critic, name="main_actor") 
        #TypeError: __init__() missing 1 required positional argument: 'output_size_critic' (main_actor에서)
        target_actor = actor_net.actor(sess, input_size_actor_s, output_size_actor, output_size_critic, name="target_actor")  
       
        #formation of network for critic net (first error NameError - input_size ciritic 등)
        main_critic = critic_net.critic(sess, input_size_critic_s,input_size_critic_a, output_size_critic, main_actor, name="main_critic") 
        target_critic = critic_net.critic(sess, input_size_critic_s,input_size_critic_a, output_size_critic, target_actor, name="target_critic")           
        
        
        _ = main_actor.initialization_a(main_critic.Objective)
        _ = target_actor.initialization_a(target_critic.Objective)
        _ = main_critic.initialization_c()
        _ = target_critic.initialization_c()
    
        sess.run(tf.global_variables_initializer()) #initializer <여기서 전체적으로 초기화해준다.>
        print("initialization complete")
        
        #Critic (first_copy)
        _ = first_copy(sess, target_scope_name="target_critic",main_scope_name="main_critic")

        #Policy (first_copy)
        _ = first_copy(sess, target_scope_name="target_actor", main_scope_name="main_actor")
        
        #Critic (Copy)
        copy_critic = copy_var_ops(target_scope_name="target_critic",main_scope_name="main_critic")
        
        #Policy (Copy)
        copy_actor =  copy_var_ops(target_scope_name="target_actor",main_scope_name="main_actor")
     
        saver_act = tf.train.Saver(max_to_keep=None) #save model (객체)

        for episode in range(0, max_episodes+1):
            
            print("Episode : {} start ".format(episode))
        
            done = False
            
            ##################### environment로부터 state를 받아온다 (observation) ###############
            state = environment.reset() #get state from environment 
            exploration_noise.reset()
            
            reward_graph = 0  
            
            #Open noise record

            reward_record = open(learning +"/reward.plt" , 'a', encoding='utf-8', newline='') 
            if episode ==0: reward_record.write('VARIABLES = "Episode", "Reward" \n') 
            #Reward를 기록하기 위함.


            while not done == True: #each episode에서 state를 진행시키는 부분 actor critic을 이 부분에 넣어야함.
      
                #Noise reset하기
                state_reward_record = open(learning + "/state_reward, episode{}.plt" .format(episode), 'a', encoding='utf-8', newline='')
                if state_step == 0: state_reward_record.write('VARIABLES = "state_step", "avg_reward" \n')   
                    
                noise_record = open(learning + "/noise, episode{}.plt" .format(episode), 'a', encoding='utf-8', newline='')
                if state_step == 0: noise_record.write('VARIABLES = "state_step", "noise" \n')                      
                    
                    

                ############### 두개의 Neural network로 학습을 시키는 부분이다 ##########
               
                if len(buffer) > buff_len and state_step % main_update_freq == 0: # train every 10 episodes
                    #print("update start")
                    loss_avg = 0
                    
                    for _ in range(train_loop_epoch):
                        #print("random_sample, step :{}" ,format(_)) #check complete
                        minibatch = random.sample(buffer, batch_size) 
                        minibatch = list(minibatch)
                        
                        #print("critic update start")
                        loss_critic, Q_old, Q_new= critic_train(main_critic, target_critic, main_actor, target_actor, minibatch)
                        
                        #print("actor update start")
                        _ = actor_train(main_actor, target_actor, main_critic, minibatch)
                        
                        loss_avg = loss_critic/train_loop_epoch +loss_avg

                    print("Loss for critic is : {}".format(loss_avg))   
                    
                    Loss_r = open(learning + "/Loss.plt", 'a')  
                    if buff_len == len(buffer)+2: Loss_r.write('VARIABLES = "state_step","Loss","buffer_len"\n')
                    Loss_r.write("%d %f %d\n" %(Loss_step, loss_avg, len(buffer)))
                    Loss_r.close()
                ##########################################################################
                
                
                #Simulation part
                if state_step % matching_feq ==0:
                    
                    #DTW-matching system
                    reward_ED_s, reward_Angle_s, state_s, next_state_s, warp_path, action_s, exact_s, Noise_s, done = environment.DTW_matching(main_actor, exploration_noise, Noise_amp, episode, state, matching_feq)
                    
                    #One-to One matching system
                    reward_dtw_s, reward_dtw_a, action_s, A_index, B_index = DS.DTW_oto_sys(warp_path, action_s, exact_s)#main_update_freq
                        
                # break part 10개단위니까 10개가 안되면 바로 Loop에서 나가도록 한다.
                if done==True:
                    break
                    

                #-------------------------- Replay buffer --------------------------#
                for _ in range(matching_feq):

                    #state = state_s[_]
                    reward_dtw_d = reward_dtw_s[_]
                    reward_dtw_angle = reward_dtw_a[_]
                    reward_ED = reward_ED_s[_]
                    reward_angle = reward_Angle_s[_]
                    action_noise = action_s[_]
                    exact = exact_s[_]
                    next_state = next_state_s[_]
                    Noise = Noise_s[_]
                    state = next_state #원래는 Loop안에서 state를 next_state로 update시켜주면서 할려고 했는데
                    
                    if action_noise < -2.0:
                        reward = -1.0
                        
                    else:
                        reward = -0.1*(0.1*reward_dtw_d) -0.1*(1.0*reward_dtw_angle)-0.6*(0.1*reward_ED)-0.2*(1.0*reward_angle)
                    
                    
                    buffer.append((state, action_noise, reward, next_state, done))
                    
     
                    #record noise
                    noise_record.write("%d %f \n" %(state_step ,np.mean(Noise)))

                    value_record = open(learning + "/prediction&exact episode{}.plt" .format(episode), 'a', encoding='utf-8', newline='')
                    if state_step ==0: value_record.write('VARIABLES = "state_step", "exact","prediction" \n')  
                    value_record.write("%d %f %f \n" %(state_step, exact, action_noise))
                    value_record.close()

                
                    #record_reward
                    state_reward_record.write("%d %f \n" %(state_step ,reward))
                
                    #summation reward
                    reward_graph = reward + reward_graph    
                    
                    state_step +=1
                   
                if state_step == step_deadline:
                    break                
                
                print("state_step")
                print(state_step)
                
                if len(buffer) > buffer_memory:
                    buffer.popleft()
                
                
                if state_step > 1 and state_step % target_update_frequency == 0:
                    #Copy main to target (critic)
                    sess.run(copy_critic)
                    
                    #Copy main to target (Actor)
                    sess.run(copy_actor)                   
                    
                    if episode == 0 and state_step < buff_len:
                        pass
                    else:
                        print("target update")
             
                #-------------------------------------------------------------------#       

                
                #High_level_problem set
                #print("high")
                #print(high_level_freq_ten)
                #print("나눠")
                #print(state_step % high_level_freq_ten)
                
                
                if state_step % high_level_freq_ten ==0 and state_step >1:

                    print("let's learn high_level")   
                    high_init, n_high = environment.high_level_reset()
                    high_dead_line = (n_high//divide)*divide
                    high_step = 0
                    
                    state_h = high_init[:]
                    
                    done_h = False

                    while(1):                                             
                        
                        #Simulation part
                        if high_step % matching_feq ==0:
                            
                            #DTW-matching system
                            reward_ED_hs, reward_Angle, state_hs, next_state_hs, warp_path_h, action_hs, exact_hs, Noise_hs,done_h = environment.h_DTW_matching(main_actor, exploration_noise, Noise_amp, episode, state_h, matching_feq)
                            
                            #One-to One matching system
                            reward_dtw_hs, reward_dtw_ha, action_hs, A_index_s, B_index_s  = DS.DTW_oto_sys(warp_path_h, action_hs, exact_hs)

                        if done_h == True: #
                            break                        
                        
                        #-------------------------- Replay buffer --------------------------#
                        for _ in range(matching_feq):

                            #state = state_s[_]
                            reward_dtw_d_h = reward_dtw_hs[_]
                            reward_dtw_angle_h = reward_dtw_ha[_]
                            reward_ED_h = reward_ED_hs[_]
                            reward_angle_h = reward_Angle[_]
                            action_h_noise = action_hs[_]
                            exact_h = exact_hs[_]
                            next_state_h = next_state_hs[_]
                            Noise_h = Noise_hs[_]
                            state_h = next_state_h #h 안붙여줬었음 뭔가 이상하다 했어
                            
                            if action_h_noise< -2.0:
                                reward_h = -1.0
                            
                            else:
                                reward_h = -0.1*(0.1*reward_dtw_d_h) -0.1*(1.0*reward_dtw_angle_h) + 0.6*(-0.1*reward_ED_h) + 0.2*(-1.0*reward_angle_h)
                    
                    
                            buffer.append((state_h, action_h_noise, reward_h, next_state_h, done_h))
                    
     
                            #record noise
                            noise_record.write("%d %f \n" %(state_step ,np.mean(Noise)))

                            value_record = open(learning + "/high_pred_ episode{} state_step{}.plt" .format(episode, state_step), 'a', encoding='utf-8', newline='')
                            if high_step ==0: value_record.write('VARIABLES = "state_step", "exact","prediction" \n')  
                            value_record.write("%d %f %f \n" %(high_step, exact_h, action_h_noise))
                            value_record.close()

                            #record_reward
                            state_reward_record.write("%d %f \n" %(state_step ,reward))
                
                            #summation reward
                            reward_graph = reward + reward_graph    
                    
                            high_step +=1

                        if high_step == (high_dead_line-2):
                            break

                        
                        if len(buffer) > buffer_memory:
                            buffer.popleft()

                        if len(buffer) > buff_len and high_step % main_update_freq == 0:
                            #Critic & Actor update part
                            loss_avg_h = 0
                    
                            minibatch_h = random.sample(buffer, batch_size) 
                            minibatch_h = list(minibatch_h)

                            #main critic update
                            loss_critic_h, Q_old_h, Q_new_h = critic_train(main_critic, target_critic, main_actor, target_actor, minibatch_h)
                                         
                            #main actor update
                            _ = actor_train(main_actor, target_actor, main_critic, minibatch_h)
                        
                            loss_avg_h = loss_critic_h/train_loop_epoch +loss_avg_h

                            print("Loss_high for critic is : {}".format(loss_avg_h))  
                            
                            Loss_r = open(learning + "/Loss.plt", 'a')  
                            Loss_r.write("%d %f %d\n" %(Loss_step, loss_avg_h, len(buffer)))
                            Loss_r.close()
                            
                        if high_step > 1 and high_step % target_update_frequency == 0:
                            #Copy main to target (critic)
                            sess.run(copy_critic)
                    
                            #Copy main to target (actor)
                            sess.run(copy_actor)                        
                        #-------------------------------------------------------------------#


                        #state_h = next_state_h
                        #high_step +=1
                        Loss_step +=1
                        print(high_step)
                        print(n_high-1)
                        
                        
                if state_step == matching_feq or state_step% validation_freq ==0:
                    print("validation")
                    name = "1_after"
                    environment.validation(main_actor, episode, state_step, name)
                    environment.make_error_set(main_actor, episode, state_step, name) #for error_data_set
                    saver_act.save(sess, './Save_check/model episode {} state_step{} name{}'.format(episode, state_step, name), global_step = None)
               
            
                noise_record.close()
                #state_step = state_step + 1   
                Loss_step = Loss_step+1
                
                #state[:] = next_state_s[matching_feq-1]
                
            reward_graph = reward_graph/state_step
            
            #Reward Graph
            reward_record.write("%d %f \n" %(episode , reward_graph))
            reward_record.close()
            #record Close
            
            state_reward_record.close()
            
            state_step = 0        
            
            

        
if __name__ == "__main__":
    
    main()
   
    print("All process is finished!")


# In[2]:


#While Loop안에서 break하면 전체가 break되는지 확인

while(1):
    
    print("good")
    
    while(1):
        
        for i in range(3):
            print("holo")
            
        break
        
        

