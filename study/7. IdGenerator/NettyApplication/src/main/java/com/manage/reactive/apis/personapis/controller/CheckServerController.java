package com.manage.reactive.apis.personapis.controller;

import com.manage.reactive.apis.personapis.domain.dto.TeamDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CheckServerController {

    @Value("${serverSpec.serverId}")
    private Long serverId;

    @Value("${serverSpec.dataCenterId}")
    private Long dataCenterId;

    //Read
    @GetMapping("/check")
    public Mono<String> getAllTeam(){
        return Mono.just("this server Id is: " + serverId.toString());
    }

}
