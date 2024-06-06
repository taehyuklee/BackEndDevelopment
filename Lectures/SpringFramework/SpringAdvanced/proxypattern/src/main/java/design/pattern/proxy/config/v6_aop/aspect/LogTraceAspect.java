package design.pattern.proxy.config.v6_aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import design.pattern.proxy.trace.TraceStatus;
import design.pattern.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect //@Aspect를 보고 Advisor로 변환해서 저장한다
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace){
        this.logTrace = logTrace;
    }

    //위의 @Around가 Pointcut, 안에 내용물이 Advice라 생각하면 된다.
    @Around("execution(* design.pattern.proxy.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{

        TraceStatus status = null;
        
        try{
            String message = joinPoint.getSignature().toShortString();

            status = logTrace.begin(message);

            Object result = joinPoint.proceed(); //실제 target의 로직

            logTrace.end(status);

            return result;

        } catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
    
}
