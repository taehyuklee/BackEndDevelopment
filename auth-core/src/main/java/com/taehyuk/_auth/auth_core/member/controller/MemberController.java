package com.taehyuk._auth.auth_core.member.controller;

import com.taehyuk._auth.auth_core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/all")
    public Map<String, String> getInfo(){
        return memberService.getInformation();
    }

}
