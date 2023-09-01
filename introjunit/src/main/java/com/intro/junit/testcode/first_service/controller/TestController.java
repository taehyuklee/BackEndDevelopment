package com.intro.junit.testcode.first_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first/service/")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("{id}")
    public String mockTest(@PathVariable("id") String id){
        return "result";
    }


}
