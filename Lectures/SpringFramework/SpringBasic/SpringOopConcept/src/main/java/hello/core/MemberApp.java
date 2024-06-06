package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    //MemberService가 잘 되는지 확인해보기 위한 것
    public static void main(String[] args) {

        //기획자를 만든다 (밖에서 의존성을 주입해 줄 객체를 만드는 것)
        //AppConfig appConfig = new AppConfig();
        //MemberService memberService = appConfig.memberSerivce(); //appConfig에서 반환된 memberSerivce구현체가 들어가 있다.

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        //name은 method이름이고, 두번째 항에는 Type이 들어간다.

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L); //findMember의 return member객체임
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName()); //syso가이나리 soutv로 쳐야함
    }
}
