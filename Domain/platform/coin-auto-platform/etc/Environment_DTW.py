#!/usr/bin/env python
# coding: utf-8

# In[4]:

import numpy as np 
import pandas as pd
import random
import csv
import os 
from fastdtw import fastdtw
from scipy.spatial.distance import euclidean
import matplotlib.pyplot as plt

#Make directory for result
learning = 'Monitoring/learning/'
if not os.path.exists(learning): os.makedirs(learning)

Test = 'Monitoring/Test/'
if not os.path.exists(Test): os.makedirs(Test)

Val_set = 'Monitoring/Test/Error_val_dataset/'
if not os.path.exists(Val_set): os.makedirs(Val_set)    

Warp_plot = 'Monitoring/Test/Warp_plot/'
if not os.path.exists(Warp_plot): os.makedirs(Warp_plot)    

Smoothing = 'Monitoring/Test/Smooth/'
if not os.path.exists(Smoothing): os.makedirs(Smoothing)    

Correct_path = 'Monitoring/Test/Correction_result/'
if not os.path.exists(Correct_path): os.makedirs(Correct_path)    

Error_plot = 'Monitoring/learning/Error_plot_learning/'
if not os.path.exists(Error_plot): os.makedirs(Error_plot)

Error_file = 'Monitoring/learning/Error_dataset_learning/'
if not os.path.exists(Error_file): os.makedirs(Error_file) 
        
        
#------------------------------------ Global method for Fourier analysis ------------------------------------#
def Fourier_analysis():

    print("start analysis")
    
    pred_fft = np.fft.fft(pred[:])/len(pred[:])  
    exact_fft = np.fft.fft(exact[:])/len(exact[:])
 
    fft_magnitude = abs(fft)         
    
    
def smoothing_MA(pred, exact, m, ep, st):
    
    #m = odd number
    k = int((m-1)/2)
    
    smoothing_pred = np.zeros([len(pred)], dtype=np.float64)
    smoothing_exact = np.zeros([len(exact)], dtype=np.float64)
            
    Smoothing_rec = open(Smoothing + "/Smoothing_MA Ep{} step{}.plt" .format(ep, st), 'a', encoding='utf-8', newline='')
    Smoothing_rec.write('VARIABLES = "time", "exact","prediction", "exact_MA", "pred_MA" \n')  
    
        
    for t in range(k, len(pred)-(k)):
        
        sum_pred = 0.0
        sum_exact= 0.0
        
        for _ in range(t-2,t+3):
            sum_pred = pred[_] + sum_pred
            sum_exact = exact[_] + sum_exact
              
        smoothing_pred[t] = sum_pred/m
        #t+1은 N을 의미한다 숫자가 0부터 시작하지 않고 2부터 시작하므로 1을 더해주는 것 
            
        smoothing_exact[t] = sum_exact/m
    
        Smoothing_rec.write("%d %f %f %f %f\n" %(t,exact[t], pred[t], smoothing_exact[t], smoothing_pred[t]))

        
    Smoothing_rec.close()        
        
        
def Pre_smoothing(m, exact):   
    
    #m = odd number
    k = int((m-1)/2)
    
    smoothing_exact = np.zeros([len(exact)], dtype=np.float64)
                  
    for t in range(k, len(exact)-(k)):

        sum_exact= 0.0
        
        for _ in range(t-2,t+3):
            sum_exact = exact[_] + sum_exact
            
        smoothing_exact[t] = sum_exact/m
     
    return smoothing_exact[k:len(exact)-(k)]
        
        

  
    
    
def plot_warp_path(warp_path, ep, state):

    #Warp_line plot
    plt.plot(warp_path[:,0], warp_path[:,1], color='red')#, marker='o'
    plt.title('Warp_path', fontsize=14)
    plt.xlabel('Exact_line', fontsize=14)
    plt.ylabel('Prediction_line', fontsize=14)
    plt.grid(True)
    #plt.show()
    plt.savefig(Warp_plot +'/Warp_path Ep{}, state{}.png'.format(ep, state), dpi=300)
    
    plt.close()
    
    #Warp_residual plot
    x_c = np.arange(len(warp_path[:,0]))
    plt.plot(x_c, warp_path[:,0]-warp_path[:,1], color='red')#, marker='o'
    plt.title('Warp_residual_plot', fontsize=14)
    plt.xlabel('time_line', fontsize=14)
    plt.ylabel('Warp_residual', fontsize=14)
    #plt.ylim([-500, 500])
    plt.grid(True)
    #plt.show()    
    plt.savefig(Warp_plot + '/Warp_residual Ep{}, state{}.png'.format(ep, state), dpi=300)
    
    plt.close()

#-------------------------------------------------------------------------------------------------------------#    

#Environment for DRL    
class env(): 

    def __init__(self):
        
        data = pd.read_csv(r"C:\Read\Standard Jongro complete1.csv", encoding = "ISO-8859-1")
        
        #mean:23.95 std:15.94047
        self.k = 2

        self.days= 48 #data for window
        self.vars = 14 #nums_variables
        self.pred_day = 24 #prediction_hrs
        
        #define high_level_set
        high_level = np.empty(0)
        self.high_level_set = np.reshape(high_level, (0, 14))
        
        #define R_high_level_set
        R_high_level = np.empty(0)
        self.R_high_level_set = np.reshape(R_high_level, (0, 14))
        
        self.r_num= (data.shape[0]) #nums of data
        
        self.frame_data = data.iloc[:,4:] #slice of data_set
        
        self.CL_name = self.frame_data.columns #Columns name   
        
        self.array_data = self.frame_data.to_numpy() #data_frame to array
        
        self.n_train = self.r_num*8//10 #nums of training data
        print("num train")
        print(self.n_train)
        self.n_val = self.r_num*2//10 #nums of validation data
        self.n_input_var = self.array_data[:,0:14].shape[1] #self.vars:23 or 14
        self.n_output_var = self.array_data[:,13:14].shape[1]
   
        self.train_data = self.array_data[2:self.n_train-2, :] #train_data set
        m=5; sm_data = Pre_smoothing(m, self.array_data[0:self.n_train, 13] ) #pre smoothing data
        k = int((m-1)/2)
        
        print("check")
        self.train_data[:,13] = sm_data[:] 
        
        print(self.train_data.shape)
        
        self.val_data = self.array_data[self.n_train:(self.n_val + self.n_train),:] #validation_data_set
        
        #validation_set save
        validation_set = pd.DataFrame(self.val_data, columns =  self.CL_name)
        validation_set.to_csv('validatoin_set.csv')

        
        #for the validation statistics (array)
        self.val_pred_set = np.zeros([self.n_val], dtype = np.float64)
        self.val_exact_set = np.zeros([self.n_val], dtype = np.float64)
        self.val_error_corr = np.zeros([self.vars], dtype = np.float64)
        self.co_num = 0
        
        #for the error set
        self.error_set = np.zeros([self.n_train,1], dtype = np.float64)
        self.v_error_set = np.zeros([self.n_val,1], dtype = np.float64)
        self.e_corr = np.zeros([self.vars], dtype = np.float64)
        self.e_v_corr = np.zeros([self.vars], dtype = np.float64)
        
        #boundary reward
        self.min_value = np.min(self.train_data[:,13:14])
        self.max_value = np.max(self.train_data[:,13:14])
        
        
        #High concentration data set (2.5 이상)
        step = 0; step_R_high = 0

        #get high_level_set
        for i in range(self.days +self.pred_day, self.n_train-2*k):
            if self.train_data[i,13:14] >= 2.5:# and self.train_data[i,13:14] <= 5.5: #self.vars =14
                self.high_level_set = np.vstack([self.high_level_set, self.train_data[i-(self.days +self.pred_day)+1:i-self.pred_day+1,0:14]])  
                #+1을 해야 24시간 간격이 된다.
                self.high_level_set = np.vstack([self.high_level_set, self.train_data[i,0:14]])

                step += 1

        self.num_high = step
        print(self.num_high)
        print("total_high")
        print(len(self.high_level_set))
        #print(self.high_level_set.shape[0])
        
        
        #validation_set save
        print("save")
        high_level = pd.DataFrame(self.high_level_set, columns =  self.CL_name)
        high_level.to_csv('high_level_set.csv')

        

        
        
    def call_high_num(self): #초기화하고 state를 보내준다

        return self.num_high, self.max_value
        
    
    def reset(self): #초기화하고 state를 보내준다
        
        init_state = self.array_data[0:self.days, 0:14].reshape(1,self.days*14)
        
        self.time = 0
        
        #check of high_initial set
        #print("initial_level")
        #level_initia = pd.DataFrame(self.array_data[0:self.days, 0:14], columns =  self.CL_name)
        #level_initia.to_csv('initi_level_set.csv')
        
        return init_state
    
    
    def validation_reset(self):
        
        init_val = self.val_data[0:self.days, 0:14].reshape (1,self.days*14)
        
        self.val_time = 0
        
        return init_val
    
     
    def high_level_reset(self):
           
        high_level_initial = self.high_level_set[0:self.days,0:14].reshape (1,self.days*14)

        self.high_time = 0
        
        #check of high_initial set
        #print("initial high_level_intitial")
        #high_level_initia = pd.DataFrame(self.high_level_set[0:self.days,0:14], columns =  self.CL_name)
        #high_level_initia.to_csv('initi_high_level_set.csv')
        
        return high_level_initial, self.num_high

    
    def R_high_level_reset(self):
           
        R_high_level_initial = self.R_high_level_set[0:self.days,0:14].reshape (1,self.days*14)

        self.R_high_time = 0

        return R_high_level_initial, self.num_R_high
    
    
    def state_num (self):
        
        self.state_num = self.array_data[:,0:14].shape[1]*self.days
        
        return self.state_num
    
    
    def action_num (self): 
        
        self.action_num = self.array_data[:,13:14].shape[1] #target prediction
        
        return self.action_num
    
    
    def train_num(self):
        
        return self.n_train    




    def DTW_matching(self, main_actor, exploration_noise, Noise_amp, episode, state, matching_feq):
        
        action_series = [] #np.zeros([matching_feq], dtype = np.float64)
        exact_series = [] #np.zeros([matching_feq], dtype = np.float64)
        state_series = [] #np.zeros([matching_feq], dtype = np.float64)
        next_state_series = [] #np.zeros([matching_feq], dtype = np.float64)
        Noise_series= []
        reward_ED = []
        reward_Angle = []
        
        done = False  
        
        x_len = 0.01
        
        for _ in range(matching_feq):
            
            Noise = Noise_amp*exploration_noise.noise()/((episode*1.0+0)+1) 
            
            action = main_actor.predict(state)
        
            action_noise = action + Noise
        
            exact= self.train_data[self.time+(self.days+self.pred_day-1), 13:14]
            
            #ED reward (vertical matching)
            reward_ED_each = abs(action_noise - exact)
            
            #Angle reward (vertical matching)
            diff_radian = ((x_len*x_len+action_noise*self.train_data[self.time+(self.days+self.pred_day-1), 13:14])/(abs(np.sqrt(x_len**2+action_noise**2))*abs(np.sqrt(x_len**2+self.train_data[self.time+(self.days+self.pred_day-1),13:14]**2))))
            
            reward_angle = abs((np.arccos(diff_radian)/np.pi*180))/180
            
 
            #Stack action, exact value, state
            action_series.append(action_noise) #[self.time]= action_noise
            exact_series.append(exact) #[self.time] = exact
            state_series.append(state) #[self.time] = state
            Noise_series.append(Noise)
            reward_ED.append(reward_ED_each)   
            reward_Angle.append(reward_angle)
      
            self.time += 1
        
            if self.time+(self.days+self.pred_day) > (self.n_train-2*self.k):
                done = True    
                break         
                
            next_state = self.train_data[self.time : self.time + (self.days), 0:14].reshape(1,(self.days)*(self.vars))
            
            #Stack next_state
            next_state_series.append(next_state) #[self.time] = next_state
            
           
            
            state[:] = next_state[:]
            
            #print("timer")
            #print(self.time)
        
        dtw_distance, warp_path = fastdtw(action_series[:],exact_series[:],dist=euclidean) #fastdtw
        
        print("Mid-distance:", dtw_distance)
        print("Warp_path_length:", len(warp_path))
        
        #if self.time+(self.days+self.pred_day) == (self.n_train):
            #done = True                
            
        return reward_ED, reward_Angle, state_series, next_state_series, warp_path, action_series, exact_series, Noise_series, done
  
    
    
    

    def h_DTW_matching(self, main_actor, exploration_noise, Noise_amp, episode, state_h, matching_feq): 
        
        action_series = [] #np.zeros([matching_feq], dtype = np.float64)
        exact_series = [] #np.zeros([matching_feq], dtype = np.float64)
        state_series = [] #np.zeros([matching_feq], dtype = np.float64)
        next_state_series = [] #np.zeros([matching_feq], dtype = np.float64)
        Noise_series= []
        reward_ED = []
        reward_Angle = []
        
        done = False
        
        x_len = 0.01
        
        for _ in range(matching_feq):
            
            Noise = Noise_amp*exploration_noise.noise()/((episode*1.0+0)+1) 
            
            action = main_actor.predict(state_h)
        
            action_noise = action + Noise
        
            #Exact value for high level
            exact_h= self.high_level_set[self.high_time+self.days, 13:14]
             
            #ED reward
            reward_ED_each = abs(action_noise - exact_h)
            
            #Angle reward
            diff_h_radian = ((x_len*x_len+action_noise*self.high_level_set[self.high_time + self.days, 13:14])/(abs(np.sqrt(x_len**2+action_noise**2))*abs(np.sqrt(x_len**2+self.high_level_set[self.high_time + self.days, 13:14]**2))))
            
            diff_h_angle = abs((np.arccos(diff_h_radian)/np.pi*180))
        
            reward_h_a = (diff_h_angle)/180            
            
 
            #Stack action, exact value, state
            action_series.append(action_noise) #[self.time]= action_noise
            exact_series.append(exact_h) #[self.time] = exact
            state_series.append(state_h) #[self.time] = state
            Noise_series.append(Noise)
            reward_ED.append(reward_ED_each)   
            reward_Angle.append(reward_h_a)
      
            self.high_time += self.days+(1)
        
            #전체 갯수보다 크면 그냥 break시켜버림 high_level은 ==조건으로 맞추기 힘들다

            if self.high_time+(self.days+self.pred_day) > (len(self.high_level_set)):
                done = True   
                break
        
            #Next state
            next_state_h = self.high_level_set[self.high_time :self.high_time + (self.days), 0:14].reshape(1,(self.days)*(self.vars))
            
            #Stack next_state
            next_state_series.append(next_state_h) #[self.time] = next_state
            
            state_h[:] = next_state_h[:]
        
        dtw_distance, warp_path = fastdtw(action_series[:],exact_series[:],dist=euclidean) #fastdtw

        
            
        return reward_ED, reward_Angle, state_series, next_state_series, warp_path, action_series, exact_series, Noise_series, done
    
    

    
    
    
    

    def validation(self, main_actor, episode, state_step, name): 
        
        #mean:23.95 std:15.94047
        
        self.val_time = 0  

        value_record = open(Test + "/Validation Scatter plot - ep{}, state_step{}, name {}.plt" .format(episode, state_step, name), 'a')  
        correlation_record = open(Test + "/Correlation_information.txt", 'a')  
        stat_record = open(Test + "/Statistics_val.txt", 'a')  
        error_csv = open(Test + "/error - ep{}, state_step{}.csv".format(episode, state_step), 'a') 
        val_error_corr = open(Test + "error_correlation.csv", 'a')  
        
        
        if self.val_time == 0: value_record.write('VARIABLES = "val_target","prediction","error","state_step" \n')
        if self.co_num == 0: 
            correlation_record.write('"Ep", "state", "correlation","R_2", "MAE", "RMSE", "dtw_distance", "MSP", "SSP", "path_length" \n')
            stat_record.write('"Ep", "step", "MAE","RMSE","pred_mean","exact_mean", "mean_diff", "pred_std", "exact_std","std_diff" \n')
            error_csv.write('time, exact, error \n')
            val_error_corr.write('"episode", "state_step", "SO2","CO","O3","NO2","PM10","Visibility","Temperature","Wind_speed","Wind_direction","Vapor_press","Sea_level_press","Solar_radiation","Total_cloud","PM2.5" \n')

        self.co_num = 1; error_val = 0
        
        while(1):
               
            val_state = self.val_data[self.val_time:self.days+self.val_time, 0:14]
        
            val_pred = main_actor.predict(val_state)
            self.val_pred_set[self.val_time] = main_actor.predict(val_state)
            
            val_target = self.val_data[self.val_time+self.days+self.pred_day, 13:14]
            self.val_exact_set[self.val_time] = self.val_data[self.val_time+self.days+self.pred_day, 13:14]
            
            #error array for correlation between error & each variables
            self.v_error_set[self.val_time + self.days + self.pred_day,0] = (self.val_exact_set[self.val_time] - self.val_pred_set[self.val_time]) 
            
            
            #monitoring part       
      
            value_record.write("%f %f %f %d\n" %(val_target, val_pred, (val_target - val_pred),self.val_time))
            error_csv.write("%d, %f, %f \n" %(self.val_time, val_target, (val_target - val_pred)))
            
                     
            
            if self.val_time+self.days+self.pred_day == (self.n_val-1): #Index0생각해야함
                
                #make data_set with error
                val_e_array = np.concatenate((self.val_data[:], self.v_error_set[:]),axis=1)
                
                
                #get Correlation between error & each variables 
                for i in range(self.vars):

                    Covariance_e = np.mean((val_e_array[:,14]- np.mean(val_e_array[:,14]))*(val_e_array[:,i]-np.mean(val_e_array[:,i])))

                    Correlation_coef = Covariance_e/(np.std(val_e_array[:,14])*np.std(val_e_array[:,i]))
            
                    self.e_v_corr[i] = Correlation_coef
            
                val_error_corr.write("%d, %d, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f \n"
                                 %(episode,state_step,self.e_v_corr[0],self.e_v_corr[1], self.e_v_corr[2] ,self.e_v_corr[3],self.e_v_corr[4], self.e_v_corr[5],self.e_v_corr[6],self.e_v_corr[7],self.e_v_corr[8],self.e_v_corr[9],self.e_v_corr[10],self.e_v_corr[11],self.e_v_corr[12],self.e_v_corr[13]))
          
                error_index = pd.Index(['error'])
                #print(self.CL_name.append([error_index]))

                error_data_set = pd.DataFrame(val_e_array, columns =  self.CL_name.append([error_index]))
                error_data_set.to_csv(Val_set + "/Error_dataset-ep{}, state_step{}, name {}.csv".format(episode, state_step, name))                
                                
                break    
                

            self.val_time +=1   
            
        
        #--------------------------------------------------- get Statistics -----------------------------------------------#
        Covariance = np.mean((self.val_pred_set[:] - np.mean(self.val_pred_set[:]))*(self.val_exact_set[:] - np.mean(self.val_exact_set[:])))
        Correlation_coef = np.corrcoef(self.val_pred_set[:], self.val_exact_set[:])[0,1]
        
        MAE = np.mean(abs(self.val_pred_set[:] - val_target[:]))  
        RMSE = np.std((self.val_pred_set[:] - val_target[:]))
        
        #dtw_path
        dtw_val,warp_path = fastdtw(self.val_pred_set[:],self.val_exact_set[:],dist=euclidean) #fastdtw
        

 
        #get dtw_statistics & save plot
        path = np.array(warp_path)
        plot_warp_path(path, episode, state_step)      
        
        #warp mean
        MSP = np.mean(abs(path[:,0] - path[:,1])) #Mean shift path
        #warp std
        SSP = np.std(abs(path[:,0] - path[:,1])) #STD shift path
        #path length
        PL = len(path)
        
        #Fourier analysis# 
        m=5; smoothing_MA(self.val_pred_set[:], self.val_exact_set[:], m, episode, state_step)
        
        
        #Record part
        correlation_record.write("%d, %d, %f, %f, %f, %f, %f, %f, %f, %f\n" %(episode, state_step, Correlation_coef, Correlation_coef**2, MAE, RMSE, dtw_val, MSP, SSP, PL))
        
        #average of error_validation
        #error_val = np.mean(abs(self.val_pred_set[:] - val_target[:]))  
        #error_std = np.std(self.val_pred_set[:] - val_target[:])
        pred_mean = np.mean(self.val_pred_set[:]); exact_mean = np.mean(val_target[:])
        pred_std = np.std(self.val_pred_set[:]); exact_std = np.std(val_target[:])
        
        stat_record.write("%d, %d, %f, %f, %f, %f, %f, %f, %f, %f \n" %(episode, state_step, MAE, RMSE,  pred_mean, exact_mean, abs(pred_mean - exact_mean) ,pred_std, exact_std, abs(pred_std - exact_std)))
        #-----------------------------------------------------------------------------------------------------------------#
        
        value_record.close()
        correlation_record.close()
        stat_record.close()
        error_csv.close()
        val_error_corr.close()

      
        
    def make_error_set(self, main_actor, episode, state_step, name): 
        
        print("make error set")
        
        error_time = 0  

        error_learn_plot = open(Error_plot + "/Error_learning_plot-ep{}, state_step{}, name {}.plt" .format(episode, state_step, name), 'a')  

        error_set_csv = open(Error_plot + "/error_set -ep{},state_step{}.csv".format(episode, state_step), 'a')  
        
        error_corr = open(Error_plot + "error_correlation_learn.csv", 'a')  
        
        if error_time == 0: 
            error_learn_plot.write('VARIABLES = "state_step","error","exact","prediction" \n')
            error_set_csv.write('time, exact, error \n') 
        if episode ==0 & error_time == 0: 
            error_corr.write('"episode", "state_step", "SO2","CO","O3","NO2","PM10","Visibility","Temperature","Wind_speed","Wind_direction","Vapor_press","Sea_level_press","Solar_radiation","Total_cloud","PM2.5" \n')

        
        while(1):
            
            #state
            error_state = self.train_data[error_time : error_time + self.days, 0:14]
        
            pred_e = main_actor.predict(error_state) #prediction
            exact_e = self.train_data[error_time + self.days + self.pred_day, 13:14] #exact value
            
            #monitoring part       
            error_learn_plot.write("%d %f %f %f \n" %(error_time, (exact_e - pred_e), exact_e, pred_e))
            error_set_csv.write("%d, %f, %f \n" %(error_time, exact_e, (exact_e - pred_e)))  
            
            self.error_set[error_time + self.days + self.pred_day,0] = (exact_e - pred_e)
            
            
            if error_time+self.days+self.pred_day == (self.n_train-1-4): #Index0생각해야함
                
                #make data_set with error
                error_array = np.concatenate((self.train_data[:], self.error_set),axis=1)
                
                
                #get Correlation between error & each variables 
                for i in range(self.vars):

                    #2D matrix유지시켜서 *하면 느려짐 따라서 1D로 바꿔저서 14:15 -->14
                    Covariance_e = np.mean((error_array[:,14]- np.mean(error_array[:,14]))*(error_array[:,i]-np.mean(error_array[:,i])))

                    Correlation_coef = Covariance_e/(np.std(error_array[:,14])*np.std(error_array[:,i]))
            
                    self.e_corr[i] = Correlation_coef
            
                error_corr.write("%d, %d, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f \n"
                                 %(episode,state_step,self.e_corr[0],self.e_corr[1], self.e_corr[2] ,self.e_corr[3],self.e_corr[4], self.e_corr[5],self.e_corr[6],self.e_corr[7],self.e_corr[8],self.e_corr[9],self.e_corr[10],self.e_corr[11],self.e_corr[12],self.e_corr[13]))
          
                error_index = pd.Index(['error'])
                #print(self.CL_name.append([error_index]))

                error_data_set = pd.DataFrame(error_array, columns =  self.CL_name.append([error_index]))
                error_data_set.to_csv(Error_file + "/Error_dataset-ep{}, state_step{}, name {}.csv".format(episode, state_step, name))
                
                break
                
                  
            error_time +=1   
                 
        
        error_learn_plot.close()
        error_set_csv.close()
        error_corr.close()
 




    def Test_with_corrector(self, main_actor_p, main_acotr_e, episode, state_step, name): 
        
        self.val_time = 0  

        value_record = open(Correct_path + "/Validation Scatter plot - ep{}, state_step{}, name {}.plt" .format(episode, state_step, name), 'a')  
        correlation_record = open(Correct_path + "/Correlation_information.txt", 'a')  
        stat_record = open(Correct_path + "/Statistics_val.txt", 'a')  
        error_csv = open(Correct_path + "/error - ep{}, state_step{}.csv".format(episode, state_step), 'a') 
        val_e_corr = open(Correct_path + "/error_correlation.csv", 'a')  
        
        
        if self.val_time == 0: value_record.write('VARIABLES = "val_target","prediction","error","state_step" \n')
        if self.co_num == 0: 
            correlation_record.write('"episode", "state_step", "correlation_coefficient","R_squared" \n')
            stat_record.write('"episode", "state_step", "error_avg","error_std","pred_mean","exact_mean", "mean_diff", "pred_std", "exact_std","std_diff" \n')
            error_csv.write('time, exact, error \n')
            val_e_corr.write('"episode", "state_step", "SO2","CO","O3","NO2","PM10","Visibility","Temperature","Wind_speed","Wind_direction","Vapor_press","Sea_level_press","Solar_radiation","Total_cloud","PM2.5" \n')

        self.co_num = 1; error_val = 0
        
        while(1):
               
            val_state = self.val_data[self.val_time:self.days+self.val_time, 0:14]
        
            #main prediction + main error prediction
            val_pred = main_actor_p.predict(val_state) + main_actor_e.predict(val_state)
            self.val_pred_set[self.val_time] = main_actor_p.predict(val_state) + main_actor_e.predict(val_state)
                        
            
            val_target = self.val_data[self.val_time+self.days+self.pred_day, 13:14]
            self.val_exact_set[self.val_time] = self.val_data[self.val_time+self.days+self.pred_day, 13:14]\
            
            #error array for correlation between error & each variables
            self.v_error_set[self.val_time + self.days + self.pred_day,0] = (self.val_exact_set[self.val_time] - self.val_pred_set[self.val_time]) 
            
            
            #monitoring part       
      
            value_record.write("%f %f %f %d\n" %(val_target, val_pred, (val_target - val_pred), self.val_time))
            error_csv.write("%d, %f, %f \n" %(self.val_time, val_target, (val_target - val_pred)))
            
                     
            
            if self.val_time+self.days+self.pred_day == (self.n_val-1): #Index0생각해야함
                
                #make data_set with error
                val_e_array = np.concatenate((self.val_data[:], self.v_error_set[:]),axis=1)
                
                
                #get Correlation between error & each variables 
                for i in range(self.vars):

                    Covariance_e = np.mean((val_e_array[:,14]- np.mean(val_e_array[:,14]))*(val_e_array[:,i]-np.mean(val_e_array[:,i])))

                    Correlation_coef = Covariance_e/(np.std(val_e_array[:,14])*np.std(val_e_array[:,i]))
            
                    self.e_v_corr[i] = Correlation_coef
            
                val_e_corr.write("%d, %d, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f \n"
                                 %(episode,state_step,self.e_v_corr[0],self.e_v_corr[1], self.e_v_corr[2] ,self.e_v_corr[3],self.e_v_corr[4], self.e_v_corr[5],self.e_v_corr[6],self.e_v_corr[7],self.e_v_corr[8],self.e_v_corr[9],self.e_v_corr[10],self.e_v_corr[11],self.e_v_corr[12],self.e_v_corr[13]))
          
                error_index = pd.Index(['error'])
                #print(self.CL_name.append([error_index]))

                error_data_set = pd.DataFrame(val_e_array, columns =  self.CL_name.append([error_index]))
                error_data_set.to_csv(Val_set + "/Error_dataset-ep{}, state_step{}, name {}.csv".format(episode, state_step, name))                
                                
                break    
                

            self.val_time +=1   
            
        #Correlation coefficient (for plot)

        Covariance = np.mean((self.val_pred_set[:] - np.mean(self.val_pred_set[:]))*(self.val_exact_set[:] - np.mean(self.val_exact_set[:])))

        Correlation_coef = Covariance/(np.std(self.val_pred_set[:])*np.std(self.val_exact_set[:]))
            
        correlation_record.write("%d, %d, %f, %f \n" %(episode, state_step, Correlation_coef, Correlation_coef**2))
        
        #average of error_validation
        error_val = np.mean((self.val_pred_set[:] - val_target[:]))  
        error_std = np.std(self.val_pred_set[:] - val_target[:])
        pred_mean = np.mean(self.val_pred_set[:]); exact_mean = np.mean(val_target[:])
        pred_std = np.std(self.val_pred_set[:]); exact_std = np.std(val_target[:])
        
        stat_record.write("%d, %d, %f, %f, %f, %f, %f, %f, %f, %f \n" %(episode, state_step, error_val,error_std,  pred_mean, exact_mean, abs(pred_mean - exact_mean) ,pred_std, exact_std, abs(pred_std - exact_std)))
        
        
        value_record.close()
        correlation_record.close()
        stat_record.close()
        error_csv.close()
        val_e_corr.close()