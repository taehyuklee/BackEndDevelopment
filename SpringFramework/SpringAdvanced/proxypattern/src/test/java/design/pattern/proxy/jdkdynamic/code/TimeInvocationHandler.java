package design.pattern.proxy.jdkdynamic.code;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

//Proxy를 동적으로 만들때 공통으로 사용하는 로직
@Slf4j //해당 InvoactionHandler는 Interface만 사용가능하다.
public class TimeInvocationHandler implements InvocationHandler{

    private final Object target; //항상 프록시는 Proxy가 호출할 대상이 있어야 한다

    public TimeInvocationHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        method.invoke(target, args); //method에 인수들 넘기는건 args로 한다 (어떤 method를 수행할지는 어떻게 알수 있어?)

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return null;
    }
    
}
