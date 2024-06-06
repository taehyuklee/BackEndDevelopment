package spring.aop.internalcall.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class CallLogAspect {
    
    @Before("execution(* spring.aop.internalcall..*.*(..))")
    public void doLong(JoinPoint joinPoint){
        log.info("aop={}", joinPoint.getSignature());
    }
}
