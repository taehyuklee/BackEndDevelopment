package orm.jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orm.jpa.domain.keymapping.MemberForKey;
import orm.jpa.service.MemberService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/insert")
    public String insertMember(MemberForKey memberForKey){
        return memberService.insertMember(memberForKey);
    }

    @PostMapping("/one-dir")
    public String oneDirTest(){
        return memberService.oneDirTeamAndMember();
    }

    @PostMapping("/bi-dir")
    public String biDirTest(){
        return memberService.biDirTeamAndMember();
    }

}
