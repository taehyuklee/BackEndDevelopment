package problem.circular_reference.user_mngt.service.impl;

import org.springframework.stereotype.Service;
import problem.circular_reference.user_mngt.service.UserService;

@Service
public class UserServiceImpl implements UserService {

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
