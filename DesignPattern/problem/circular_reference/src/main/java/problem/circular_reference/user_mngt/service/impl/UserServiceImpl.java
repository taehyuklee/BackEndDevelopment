package problem.circular_reference.user_mngt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import problem.circular_reference.user_mngt.service.UserService;
import problem.circular_reference.usergrp_mngt.service.UserGrpService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserGrpService userGrpService;

    @Override
    public void createUser(){
        System.out.println("사용자가 생성되었습니다");
    }

    @Override
    public void readUser(){

    }

    @Override
    public void updateUser(){

    }

    @Override
    public void deleteUser(){

    }
}
