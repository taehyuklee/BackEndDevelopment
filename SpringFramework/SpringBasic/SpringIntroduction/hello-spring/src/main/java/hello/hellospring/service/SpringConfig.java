package hello.hellospring.service;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.swing.text.html.parser.Entity;

@Configuration
public class SpringConfig {

    //private DataSource dataSource;
    private EntityManager em; // 이렇게 JPA의 em을 받을수 있다.

    @Autowired
    public SpringConfig(EntityManager em){ //알아서 spring에서 em을 넣어준다.
        this.em = em;
        //알아서 dataSource에 주입을 해준다.
    }

    @Bean // 내가 이제 스프링 빈을 등록할거야 라고 생각하면 된다.
    public MemberService memberService(){
        return new MemberService(memberRepository());
        //여기 MemberService에는 memberRepository를 넣어줘야 한다.
    }

    @Bean // 위에 MemberService에 넣어줘야하니까
    public MemberRepository memberRepository(){

        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        //return new JpaMemberRepository(em);
        //JpaMemberRepository는 entity manager라는 객체가 필요하다 em
        //위에까지는 dataSource로 받았는데
        return new JpaMemberRepository(em);
    }

    //Controller는 어쩔수 없다 직접 쳐줘야 한다. service, Repo등은 Config될 것이다.

}
