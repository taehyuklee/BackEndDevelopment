package design.pattern.proxy.config.v2_dynamicproxy.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.util.PatternMatchUtils;

import design.pattern.proxy.trace.TraceStatus;
import design.pattern.proxy.trace.logtrace.LogTrace;

public class LogTraceFilterHandler implements InvocationHandler{
    
    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns){
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //메서드 이름 필터
        String methodNmae = method.getName();
        //save, request, reque*, *est
        if(!PatternMatchUtils.simpleMatch(patterns, methodNmae)){
            return method.invoke(target, args);
        }

        TraceStatus status = null;
        
        try{
            //넘어온 메타정보를 가지고 "OrderController.request()" message를 업데이트 할 것이다. 
            //Meta정보에서 Class이름 + method 이름으로 아래 메시지를 만들수 있다.
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";

            status = logTrace.begin(message);

            Object result = method.invoke(target, args); //실제 target의 로직

            logTrace.end(status);

            return result;

        } catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }

    }
}
