package com.mongoDB.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value ="/ApiWithFile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createApiWithFile(@Valid @RequestPart("admApiDto") AdmApiDto admApiDto, @RequestPart(value="jsonFile") MultipartFile jsonFile){
        serviceApi.createApiWithFile(admApiDto, jsonFile);
    }

    // @PostMapping(value ="/ApiOnlyhFile")
    // public void createApiWithFile(@Valid @RequestPart("admApiDto") AdmApiDto admApiDto, @RequestPart(value="jsonFile") MultipartFile jsonFile){
    //     serviceApi.createApiWithFile(admApiDto, jsonFile);
    // }

    @DeleteMapping("/Api")
    public void deleteApi(@Valid @RequestParam("Api") String apiId){

    }
}
