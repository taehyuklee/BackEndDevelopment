package spring.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Aspect //Aspect는 ComponentScan 대상이 아니다.
public class AspectV1 {
    
    @Around("execution(* spring.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ 
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
        return joinPoint.proceed();
    }
}
