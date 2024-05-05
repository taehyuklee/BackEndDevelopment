package problem.circular_reference.usergrp_mngt.service.imple;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import problem.circular_reference.user_mngt.service.UserService;
import problem.circular_reference.usergrp_mngt.service.UserGrpService;

@Service
@RequiredArgsConstructor
public class UserGrpServiceImpl implements UserGrpService {

    private final UserService userService;


    @Override
    public void createUserGrp() {
        System.out.println("사용자 그룹이 생성되었습니다");
    }

    @Override
    public void readUserGrp() {

    }

    @Override
    public void updateUserGrp() {

    }

    @Override
    public void deleteUserGrp() {

    }
}
