package db.partition.partitioning.aop.proxy;

import db.partition.partitioning.aop.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)") //파라미터가 있을때는 Retry retry 파라미터 받은거에서 @annotation쪽으로 알아서 대체가 된다고 한다.
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable{
        log.info("[retry] {} retry ={}", joinPoint.getSignature(), retry);
        int maxRetry = retry.value();

        Exception exceptionHolder = null;

        for(int retryCount=1; retryCount<=maxRetry; retryCount++){
            try{
                log.info("[retry] try count = {}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            }catch(Exception e){
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;

    }
}