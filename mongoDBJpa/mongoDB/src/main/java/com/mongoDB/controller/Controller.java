package com.mongoDB.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongoDB.domain.dto.AdmApiDto;
import com.mongoDB.service.ServiceApi;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final ServiceApi serviceApi;

    //Api CRUD
    @PostMapping("/Api")
    public void createApi(@Valid @RequestBody AdmApiDto admApiDto){
        serviceApi.createApi(admApiDto);
    }

    @DeleteMapping("/Api")
    public void deleteApi(@Valid @RequestParam("Api") String apiId){

    }

    
}
