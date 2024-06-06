package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        //1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberSerivce2 = appConfig.memberService();

        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService1 = " + memberSerivce2);

        //memberService1 != memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberSerivce2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        //new SingletonService(); private으로 생성자가 막혀있는 것을 확인함.
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + singletonService1);
        System.out.println("memberService1 = " + singletonService2);

        //singletonService1 == singletonService2
        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
        /*isSameAs, isEqualTo랑 무슨 차이냐?
        * same은 Java == instance비교할때 사용, equal은 object객체의 .equals()와 같다.
        * */
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
        //AppConfig appConfig = new AppConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberSerivce2 = ac.getBean("memberService", MemberService.class);

        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService1 = " + memberSerivce2);

        //memberService1 == memberService2
        Assertions.assertThat(memberService1).isSameAs(memberSerivce2);
    }

}
