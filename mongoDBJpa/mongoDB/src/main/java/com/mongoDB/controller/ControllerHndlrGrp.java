package com.mongoDB.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongoDB.domain.dto.AdmHndlrGrpDto;
import com.mongoDB.service.ServiceApi;
import com.mongoDB.service.ServiceHndlrGrp;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ControllerHndlrGrp {

    private final ServiceHndlrGrp serviceHndlrGrp;

    @PostMapping("/ApiHndlrGrp")
    public String createApiHnldrGrp(@Valid @RequestBody AdmHndlrGrpDto admHndlrGrpDto){

        // System.out.println("//////////////////");
        // System.out.println(admHndlrGrpDto);

        serviceHndlrGrp.createApiHndlrGrp(admHndlrGrpDto);

        return "잘 된 것 같음";
    }
    
}
