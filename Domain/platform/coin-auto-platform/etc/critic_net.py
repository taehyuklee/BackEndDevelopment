#!/usr/bin/env python
# coding: utf-8

# In[ ]:

import numpy as np
import tensorflow.compat.v1 as tf
import tensorflow as tf2


class critic:

    def __init__(self, session, input_size_critic_s, input_size_critic_a, output_size_critic, actor, name="main"): 
        #생선자를 만들어서 Class안의 Attribute (metod, members 등) 근데 이렇게 함수 받을때 :는 무슨 표시일까?

        #객체 변수들에 대해서 다음과 같이 정의 한다. session, input_size, output_size, name 
        self.session = session
        
        #이걸 action과 state로 다시 나눠서 해줘야한다
        self.input_size_critic_s = input_size_critic_s
        self.input_size_critic_a = input_size_critic_a
        self.output_size_critic = output_size_critic
        
                        ################## state feed, action feed & actor connection #####################
        self.input_critic_state = tf.compat.v1.placeholder(tf.float32, [None, self.input_size_critic_s], name="input_critic_state")    
        self.feed_action = tf.compat.v1.placeholder(tf.float32, [None, self.input_size_critic_a], name="feed_action")
        self.action_pred = actor.action_pred
        
        self.input_total_size_c = self.input_size_critic_s + self.input_size_critic_a
        
        self.net_name = name
        #self.action = tf.placeholder("float", [None, action_nums]) #원래는 grad Q X grad Policy 하려고 했던거
        #self.Q_grad = tf.gradients(self._Qpred, self.action)

        #self.build_network()# class를 만들면 알아서 객체의 Neural network를 만들게 해 놓은거임.
        
        #아래의 두개는 W1 B1 W2 B2 Trainable variable을 공유한다.
        print(name)
        self.Q_pred = self.build_network (self.feed_action, "Critic_net connected - for action_feed") #critic update할때 사용
        print(name)
        self.Objective = self.build_network (self.action_pred, "Critic_net connected - for actor_feed") #actor update할때 사용.
        
    
    
    def build_network(self, action, sentence, h1_size = 128, h2_size = 128, h3_size = 64, h4_size = 32): #, h_size=10, l_rate=0.001

        #나중에 self.input_critic_action으로 미분해줘야 함
        
        with tf.compat.v1.variable_scope(self.net_name, reuse=tf.compat.v1.AUTO_REUSE): #정확이 이것은 무엇을 의미하는 것일까?
            #변수의 범위는 변수가 효력을 미치는 영역을 의미한다 (변수의  scope) -namespace벗어나면 영향력이 없어진다.
            
            #self.input_critic_state = tf.compat.v1.placeholder(tf.float32, [None, self.input_size_critic_s], name="input_critic_state")  
            #안에다 위와같이 넣으면 뭔가 계속 꼬였다.
            self.input_critic_action = action

            self.X_input = tf.concat([self.input_critic_state, self.input_critic_action], -1) #일렬로 만든다 reshape효과까지 합쳐서
            #나중에 그림으로 그려보자
            
            
            
            W1_S = [self.input_total_size_c, h1_size]
            W1_std = np.sqrt(1)/np.sqrt(np.prod(W1_S[:-1]))
            #Trainable Parameter W_c1, B_c1
            self.W_c1 = tf.compat.v1.get_variable("W_c1", shape = W1_S, initializer = tf.random_normal_initializer(mean=0.0, stddev=W1_std, seed=None))
            self.B_c1 = tf.compat.v1.get_variable("B_c1", shape = [1,h1_size], initializer = tf.initializers.zeros())
            
            
            
            W2_S = [h1_size, h2_size]
            W2_std = np.sqrt(1)/np.sqrt(np.prod(W2_S[:-1]))
            #Trainable Parameter W_c2, B_c2
            self.W_c2 = tf.compat.v1.get_variable("W_c2", shape = W2_S, initializer = tf.random_normal_initializer(mean=0.0,stddev=W2_std, seed=None))   
            self.B_c2 = tf.compat.v1.get_variable("B_c2", shape = [1, h2_size], initializer = tf.initializers.zeros())
            
            
            W3_S = [h2_size, h3_size]
            W3_std = 0.1*np.sqrt(1)/np.sqrt(np.prod(W3_S[:-1]))
            #Trainable Parameter W_c1, B_c1
            self.W_c3 = tf.compat.v1.get_variable("W_c3", shape = W3_S, initializer = tf.random_normal_initializer(mean=0.0, stddev=W3_std, seed=None))
            self.B_c3 = tf.compat.v1.get_variable("B_c3", shape = [1, h3_size], initializer = tf.initializers.zeros())            
            
            
            W4_S = [h3_size, self.output_size_critic]
            W4_std = 0.1*np.sqrt(1)/np.sqrt(np.prod(W4_S[:-1]))
            #Trainable Parameter W_c1, B_c1
            self.W_c4 = tf.compat.v1.get_variable("W_c4", shape = W4_S, initializer = tf.random_normal_initializer(mean=0.0, stddev=W4_std, seed=None))
            self.B_c4 = tf.compat.v1.get_variable("B_c4", shape = [1,self.output_size_critic], initializer = tf.initializers.zeros())         
            
            
            #연결층 만들기 
            layer1 = tf.matmul(self.X_input, self.W_c1) + self.B_c1
            #active1 = tf.nn.relu(layer1)
            active1 = tf.nn.leaky_relu(layer1, alpha=0.01, name=None)
            #active1 = tf.nn.dropout(active1, keep_prob = 0.3)
            
            #tf.nn.leaky_relu(features, alpha=0.2, name=None)
            
            
            layer2 = tf.matmul(active1, self.W_c2) + self.B_c2
            #active2 = tf.nn.relu(layer2)
            active2 = tf.nn.leaky_relu(layer2, alpha=0.01, name=None)
            #active2 = tf.nn.dropout(active2, keep_prob = 0.3)
            
            
            layer3 = tf.matmul(active2, self.W_c3) + self.B_c3
            active3 = tf.nn.leaky_relu(layer3, alpha=0.01, name=None)
            #active3 = tf.nn.dropout(active3, keep_prob = 0.3)
            
            layer4 = tf.matmul(active3, self.W_c4) + self.B_c4
            
        
            Q_pred = 0.01*layer4
              
            self.Q_target = tf.compat.v1.placeholder(shape=[None, self.output_size_critic], dtype = tf.float32)          
             
            print(sentence)
        
        return Q_pred
        
    def initialization_c (self, l_rate=0.00001, B = 0.00001):       
            #객체에 대한 Loss function을 만든다
            self.regular = tf.nn.l2_loss(self.W_c1)+tf.nn.l2_loss(self.W_c2)+tf.nn.l2_loss(self.W_c3) + tf.nn.l2_loss(self.W_c4)
            self.optimizer_c = tf.compat.v1.train.AdamOptimizer(learning_rate=l_rate, name = 'critic_adam')
            self.session.run(tf.compat.v1.variables_initializer(self.optimizer_c.variables()))
            
            #dtw.dtw(self.val_pred_set[:],self.val_exact_set[:],keep_internals=True).distance
            
            #tf.keras.losses.cosine(y_true, y_pred, axis=-1)
            #self.loss = tf.reduce_mean(tf.square(self.Q_target - self.Q_pred)) + B*self.regular

            self.loss = tf.reduce_mean(tf2.keras.losses.MAE(self.Q_target, self.Q_pred))+ B*self.regular
            
            self.train = self.optimizer_c.minimize(self.loss, var_list = [self.W_c1, self.B_c1, self.W_c2, self.B_c2,self.W_c3, self.B_c3,self.W_c4, self.B_c4])
            

    #Predict함수는 상태를 받아서 결과를 돌려달라가 된다.
    #신경회로망을 짜고 
    def predict(self, state, action): # ,action, 이부분은 앞에서 action을 직접 이어보면서 더 이상 필요 없어졌다.
        input_state = np.reshape(state, [1, self.input_size_critic_s])
        input_action = np.reshape(action, [1, self.input_size_critic_a])

        #input_action = np.reshape(action, [1, self.input_size_critic_a]) #여기서 Policy로부터 받아온 action이고 critic으로 보내지면서 연결이 된다.
        return self.session.run(self.Q_pred, feed_dict={self.input_critic_state: input_state, self.feed_action: input_action})
    #여기도 main_actor라고 되어 있었음
        #self.input_critic_action:input_action
        
    #Update (학습시키는거 데이터들을 받아서 self.session 실행시켜 돌려보낸다)                   
    def update(self, x_state_stack, x_action_stack, y_stack):
        feed = {self.input_critic_state: x_state_stack, self.feed_action: x_action_stack, self.Q_target: y_stack} 
        return self.session.run([self.loss, self.train], feed_dict=feed)
    
    '''
    Update부분 설명
    #self.loss에서는 loss만을 표기하고 self.train에서 학습이 일어난다.
    #feed로 self.X_input를 넣어주면 그 안의 graph에 있는 self.X_input에 그 값이 넣어진다.
    #X_input은 self.input_critic_state, self.input_critic_action로 이루어져 있고 이걸로 넣어줘야 한다 (안에서 합쳐져 있으므로)
    '''

