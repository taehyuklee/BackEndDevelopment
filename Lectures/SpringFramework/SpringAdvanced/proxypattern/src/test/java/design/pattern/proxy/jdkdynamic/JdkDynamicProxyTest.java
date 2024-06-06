package design.pattern.proxy.jdkdynamic;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

import design.pattern.proxy.jdkdynamic.code.AImpl;
import design.pattern.proxy.jdkdynamic.code.AInterface;
import design.pattern.proxy.jdkdynamic.code.BImpl;
import design.pattern.proxy.jdkdynamic.code.BInterface;
import design.pattern.proxy.jdkdynamic.code.TimeInvocationHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JdkDynamicProxyTest {
    
    @Test
    void dynamicA(){
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target); //실제 호출 대상을 Proxy에 넣어주는 역할

        Object proxy = Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler); //동적으로 proxy객체가 생성된다.
        
        //AInterface를 기반으로 만들어진다 (InvocationHandler를 이용해서 proxy객체가 생성된다. (인터페이스))
        AInterface proxyA = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler); 
        /*Interface가 아니면 java.lang.IllegalArgumentException: design.pattern.proxy.jdkdynamic.code.AImpl is not an interface 해당 에러가 뜬다 */

        proxyA.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); //proxyClass=class jdk.proxy2.$Proxy8 동적 프록시
    }

    @Test
    void dynamicB(){
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        Object proxy = Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler); //동적으로 proxy객체가 생성된다.
        
        //AInterface를 기반으로 만들어진다
        BInterface proxyA = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);

        proxyA.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }


}
