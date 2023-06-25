package design.pattern.proxy.common.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import lombok.extern.slf4j.Slf4j;

//Spring에서 제공해주는건 여기에 부가로직만 구현해주면 된다.
@Slf4j
public class TimeAdvice implements MethodInterceptor{

    //원래 target을 넣어줬었는데 Proxy Factory에서 알아서 target을 넣어줘서 여기서는 안넣어줘도 된다.
    
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        /* invocation안에 Proxy관련 target.Class, methods 들과 같은 메타정보들을 모두 포함하고 있다. - 이전 ProxyFactory에서 target정보를 넘겨받는다.
         * 이렇게 proceed()만 하면 알아서 찾아서 다 해준다. 
         */
        Object result = invocation.proceed(); //이미 알아서 다 만들었다.

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
    
}
