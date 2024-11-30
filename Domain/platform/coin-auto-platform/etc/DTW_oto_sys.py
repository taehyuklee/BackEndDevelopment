#!/usr/bin/env python
# coding: utf-8

# In[ ]:


#Module version

import numpy as np 
import random
from fastdtw import fastdtw
from scipy.spatial.distance import euclidean


def get_farthest_point(stack, ref):
    
    things=[]
    
    for p in stack:

        things.append(abs(p[1]- ref))
    
    farthest_point = max(stack, key=lambda x:abs(x[1]-ref))
    
    return farthest_point


def get_distance(A_m, B_m, A, B):
    
    reward = []
    action = []
    
    for i in range(len(A_m)):
        reward.append(np.sqrt((A[A_m[i]] - B[B_m[i]])**2 + (A_m[i] - B_m[i])**2))
        action.append(A[A_m[i]])

    return reward, action


def get_angle(A_m, B_m, A, B):
    #Angle reward
    
    r_angle =[]
    
    x_len = 0.05
    
    for i in range(len(A_m)):
        
        #time 거리를 빼주고 x_len을 더해줌으로써 일반화시켜준다.
        min_value = min(A_m[i], B_m[i])
        
        A_len = A_m[i] - min_value + x_len
        B_len = B_m[i] - min_value + x_len        
        
        
        radian = ((A_len*B_len +A[A_m[i]]*B[B_m[i]])/(abs(np.sqrt(A_len**2+A[A_m[i]]**2))*abs(np.sqrt(B_len**2+B[B_m[i]]**2))))

        angle = abs((np.arccos(radian)/np.pi*180))
        
        angle_norm = angle/180
        
        r_angle.append(angle_norm)
        
    return r_angle




def DTW_oto_sys(path, A, B):

    A_matched = []
    B_matched = []

    #N^2 complexity -일단 시험해보고 더 좋게 짜지 뭐 나중에 nlogn까지 목표로 해보자
    for ref in range(len(A)):
    
        stack = []
    
        for p in path:
        
            if p[0] == ref:

                stack.append(p)


        far_point = get_farthest_point(stack, ref)
        A_matched.append(far_point[0])
        B_matched.append(far_point[1])

    reward_s, action_s = get_distance(A_matched, B_matched, A, B)   
    reward_a = get_angle(A_matched, B_matched, A, B)
        
    return reward_s, reward_a, action_s, A_matched, B_matched
    
    

