package problem.circular_reference.usergrp_mngt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import problem.circular_reference.usergrp_mngt.service.UserGrpService;

@RestController
@RequiredArgsConstructor
public class UserGrpController {

    private final UserGrpService userGrpService;

    @GetMapping("/usergrp")
    public void createUserGrp(){
        userGrpService.createUserGrp();
    }

}
