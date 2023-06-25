package design.pattern.proxy.config.v3_proxyfactory.advice;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import design.pattern.proxy.trace.TraceStatus;
import design.pattern.proxy.trace.logtrace.LogTrace;

public class LogTraceAdvice implements MethodInterceptor{

    private final LogTrace logTrace;

    public LogTraceAdvice(LogTrace logTrace){
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TraceStatus status = null;
        
        try{

            Method method = invocation.getMethod();

            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";

            status = logTrace.begin(message);

            Object result = invocation.proceed(); //실제 target의 로직

            logTrace.end(status);

            return result;

        } catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
    
}
