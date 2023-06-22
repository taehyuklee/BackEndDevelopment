package spring.aop.pcd;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import spring.aop.member.MemberService;

@Slf4j
@Import(AtAnnotationTest.AtAnnotationTestAspect.class)
@SpringBootTest
public class AtAnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
    void success(){
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class AtAnnotationTestAspect{

        @Around("@annotation(spring.aop.member.annotation.MethodAop)") //Annotation이 있는 부분
        public Object doAtAnnotation(ProceedingJoinPoint joinPoint) throws Throwable{
            log.info("[@annotation] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
    
}
