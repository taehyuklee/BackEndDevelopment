package com.mongoDB.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongoDB.domain.dto.AdmHndlrDto;
import com.mongoDB.domain.entity.AdmHndlr;
import com.mongoDB.service.ServiceApiHndlr;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ControllerApiHndlr {

    private final ServiceApiHndlr serviceApiHndlr;


    @PostMapping("/ApiHndlr")
    public String createApiHnldr(@Valid @RequestBody AdmHndlrDto admHndlr){
        /*
         * AdmHndlrDto(hndlrId=null, trtSect=null, sttus=null, type=null, order=0, bodyRfrn=false, cstmClassAdr=null, cstmClassSrc=null, cstmClassNm=null, desc=null)
         * 왜 계속 null이 들어가나 했더니, @RequestBody를 안넣었었다. 
         * 
         */ 

        System.out.println(admHndlr);
        System.out.println("///////////////////");

        System.out.println("나 여기 있습니다.");
        serviceApiHndlr.createApiHndlr(admHndlr);

        return "잘 된 것 같음";
    }
    
    @DeleteMapping("/ApiHndlr")
    public void deleteApiHndlr(@Valid @RequestParam("Api") String hndlrId){

    }
    
}
