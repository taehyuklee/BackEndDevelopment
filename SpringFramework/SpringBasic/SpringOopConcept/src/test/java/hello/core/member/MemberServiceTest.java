package hello.core.member;

import hello.core.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService; //구현체가 필요한데, 구현체의 객체를 Impl을 통해서 만들어서 준다.

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join(){
        //given ~이런게 주어졌을때
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when ~그리고 이렇게 했을때
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then ~이렇게 된다
        Assertions.assertEquals(member, findMember);
    }
}
