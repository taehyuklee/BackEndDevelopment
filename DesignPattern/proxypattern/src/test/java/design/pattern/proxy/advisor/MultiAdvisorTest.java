package design.pattern.proxy.advisor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import design.pattern.proxy.common.service.ServiceImpl;
import design.pattern.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;

public class MultiAdvisorTest {
    
    @Test
    @DisplayName("여러 프록시")
    void multiAdvisorTest1(){
        //client -> proxy2(advisor2) -> proxy1(advisor1) -> target

        //프록시1 생성
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory1 = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor1= new  DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1()); //Advisor는 하나의 PointerCut과 Advice를 가지고 있다.

        proxyFactory1.addAdvisor(advisor1);
        ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

        //프록시2 생성, proxy1 입력
        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        DefaultPointcutAdvisor advisor2 = new  DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2()); 
        proxyFactory1.addAdvisor(advisor2);
        ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

        proxy2.save();

        //이렇게 Advisor를 여러개 만들어 사용할수 있지만, 문제가 있다 proxy를 만들때마다 Adivsor를 첨가해야 한다.
        
    }


    @Test
    @DisplayName("하나의 프록시, 여러 어드바이저")
    void multiAdvisorTest2(){
        //client -> proxy -> advisor2 -> adivosr1 -> target


        DefaultPointcutAdvisor advisor1= new  DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1()); //Advisor는 하나의 PointerCut과 Advice를 가지고 있다.
        DefaultPointcutAdvisor advisor2 = new  DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2()); 


        //프록시2 생성, target -> proxy1 입력
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory1 = new ProxyFactory(target);

        //내가 원하는 부가기능 순서대로 넣어줘야 한다.
        proxyFactory1.addAdvisor(advisor2);
        proxyFactory1.addAdvisor(advisor1);
        ServiceInterface proxy = (ServiceInterface) proxyFactory1.getProxy();

        proxy.save();
        
    }





    @Slf4j
    static class Advice1 implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("adivce1 호출");
            return invocation.proceed();
        }

    }

    @Slf4j
    static class Advice2 implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("adivce2 호출");
            return invocation.proceed();
        }

    }
}
