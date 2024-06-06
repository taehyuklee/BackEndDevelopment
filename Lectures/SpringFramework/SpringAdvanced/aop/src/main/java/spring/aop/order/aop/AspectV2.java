package spring.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Aspect //AspectV2는 Pointcut과 Advice를 나눠서 할 경우
public class AspectV2 {

    //이렇게 PointCut을 만들면 다른곳에서도 이 PointCut을 사용할수 있게 된다.
    @Pointcut("execution(* spring.aop.order..*(..))")
    private void allOrder(){} //pointcut signature
    
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ 
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
        return joinPoint.proceed();
    }
}
