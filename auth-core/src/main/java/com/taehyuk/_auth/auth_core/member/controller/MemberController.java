package com.taehyuk._auth.auth_core.member.controller;

import com.taehyuk._auth.auth_core.member.domain.dto.MemberDto;
import com.taehyuk._auth.auth_core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/new")
    public String registerProcess(@RequestBody MemberDto memberDto) {
        memberService.registerProcess(memberDto);
        return "Ok";
    }

    @GetMapping("/all")
    public Map<String, String> getInfo(){
        System.out.println("stop");
        return memberService.getInformation();
    }

}
