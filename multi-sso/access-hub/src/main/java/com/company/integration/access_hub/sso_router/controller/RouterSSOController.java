package com.company.integration.access_hub.sso_router.controller;

import com.company.integration.access_hub.sso_router.service.RouterSSOService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multi-sso")
@RequiredArgsConstructor
public class RouterSSOController {

    private final RouterSSOService routerSSOService;

    @PostMapping("/route")
    public String routingSSO(){
        System.out.println("let's route to other spot");
        return routerSSOService.routingService();
    }
}
