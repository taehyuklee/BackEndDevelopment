package spring.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AspectV5Order {
   
    /*
     *  @Order(1) //Advice순서는 이와 같이 method단위에 Order(1)를 아무리 붙여도 소용이 없다. Class단위에 즉 @Aspect단위에 먹는다.
        @Around("spring.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ 
            log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
            return joinPoint.proceed();
        }
        결과적으로 method가 아니라 nested Class단위로 만들어야 한다.
     */

    @Aspect
    @Order(2)
    public static class LogAspect{

        //왜 static으로 꼭 해야 하는것인가? - Spring은 중첩 클래스의 인스턴스를 직접 생성하여 사용하는 것이 아니라, 클래스 레벨에서 정의된 타입을 통해 프록시 객체를 생성하고 관리합니다.
        //따라서 중첩 클래스가 static으로 정의되지 않으면, 중첩 클래스의 인스턴스화를 위해 외부 클래스의 인스턴스가 필요하게 됩니다. 그러나 중첩 클래스는 static이 아니기 때문에 외부 클래스의 인스턴스를 생성하지 않고는 중첩 클래스의 인스턴스를 생성할 수 없습니다. 따라서 Spring은 중첩 클래스를 빈으로 등록할 수 없게 되고, 에러가 발생하게 됩니다.
        @Around("spring.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ 
            log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
            return joinPoint.proceed();
        }

    }

    @Aspect
    @Order(1)
    public static class TxAspect{
        @Around("spring.aop.order.aop.Pointcuts.orderAndService()")
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


}
