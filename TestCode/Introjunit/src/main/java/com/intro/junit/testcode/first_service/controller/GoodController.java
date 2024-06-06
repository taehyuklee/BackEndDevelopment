package com.intro.junit.testcode.first_service.controller;

import com.intro.junit.testcode.first_service.domain.entity.GoodEntity;
import com.intro.junit.testcode.first_service.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/first/service/")
@RequiredArgsConstructor
public class GoodController {

    private GoodService goodService;

    private GoodEntity goodEntity;

    @GetMapping("{id}")
    public String mockTest(@PathVariable("id") String id){
        return "result";
    }

    //Create
    @PostMapping("/service")
    public String create(){
        return "Success";
    }


    //Read
    @GetMapping("/service")
    public String find(){
        return "Success";
    }


    //Update
    @PutMapping("/serivce")
    public String update(){
        return "Success";
    }


    //Delete
    @DeleteMapping("/delete")
    public String delete(){
        return "Success";
    }


}
