package spring.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class AspectV3 {

    //이렇게 PointCut을 만들면 다른곳에서도 이 PointCut을 사용할수 있게 된다.
    @Pointcut("execution(* spring.aop.order..*(..))")
    public void allOrder(){} //pointcut signature

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}
    
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ 
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
        return joinPoint.proceed();
    }

    //order패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service인 경우
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{ 
        
        try{
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            return result;
        }catch (Exception e){
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            throw e;
        }finally{
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature()); 
        }
    }

    
}
