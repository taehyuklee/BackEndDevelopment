package spring.aop.order.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV6Advice {
    
    
    // @Around("spring.aop.order.aop.Pointcuts.orderAndService()")
    // public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{ 
        
    //     try{
    //         //@Before
    //         log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
    //         Object result = joinPoint.proceed();

    //         //@AfterReturning
    //         log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
    //         return result;
    //     }catch (Exception e){
    //         //AfterThrowing
    //         log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
    //         throw e;
    //     }finally{
    //         //@After
    //         log.info("[리소스 릴리즈] {}", joinPoint.getSignature()); 
    //     }
    // }


    //Target의 joinPoint가 실행되기 전에 남기기위해서 (간단하게 사용하기 위함)
    @Before("spring.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint){
        log.info("[before]{}", joinPoint.getSignature());
    }

    @AfterReturning(value="spring.aop.order.aop.Pointcuts.orderAndService()", returning= "result")
    public void doReturn(JoinPoint joinPoint, Object result){
        log.info("[return]{}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value="spring.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doReturn(JoinPoint joinPoint, Exception ex){
        log.info("[ex]{} message={}",  ex);
    }

    @After(value="spring.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint){
        log.info("[after]{} message={}", joinPoint.getSignature());
    }
}
