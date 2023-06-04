package design.pattern.proxy.proxyfactory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import design.pattern.proxy.common.advice.TimeAdvice;
import design.pattern.proxy.common.service.ConcreteService;
import design.pattern.proxy.common.service.ServiceImpl;
import design.pattern.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrxoyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy(){

        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); //여기서 ProxyFactory에게 이미 target정보를 넘겼기때문에
        proxyFactory.addAdvice(new TimeAdvice()); //여기서 target정보를 이미 알고 있다고 본다.
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); 
        
        //target을 ServiceInterface로(구현했으니까) 받았으니까 결과가 $Proxy9으로 나왔다. (jdk동적 프록시가 적용됨)
        /*
         * 15:39:31.975 [main] INFO design.pattern.proxy.proxyfactory.PrxoyFactoryTest -- targetClass=class design.pattern.proxy.common.service.ServiceImpl
         * 15:39:31.979 [main] INFO design.pattern.proxy.proxyfactory.PrxoyFactoryTest -- proxyClass=class jdk.proxy2.$Proxy9
         */

        proxy.save();

        //Proxy Factory를 쓸때만 잘 적용됐는지를 확인할수 있다. (내가 직접 만든건 안먹는다)
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }


    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy(){

        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target); //여기서 ProxyFactory에게 이미 target정보를 넘겼기때문에
        proxyFactory.addAdvice(new TimeAdvice()); 
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); 
        
        proxy.call();

        //Proxy Factory를 쓸때만 잘 적용됐는지를 확인할수 있다. (내가 직접 만든건 안먹는다)
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
       
    }


    @Test
    @DisplayName("proxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass(){

        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); 
        proxyFactory.setProxyTargetClass(true); // 이걸 true로 설정하면 항상 CGLIB로 설정된다. (Interface무시하고 ServiceImpl을 상속받아서 만든다.)
        proxyFactory.addAdvice(new TimeAdvice()); 
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); 

        proxy.save();

        //Proxy Factory를 쓸때만 잘 적용됐는지를 확인할수 있다. (내가 직접 만든건 안먹는다)
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }


    /**
     * > 참고
     * > 스프링 부트는 AOP를 적용할 때 기본적으로 proxyTargetClass=true 로 설정해서 사용한다.
     * > 따라서 인터페이스가 있어도 항상 CGLIB를 사용해서 구체 클래스를 기반으로 프록시를 생성한다.
     * > 자세한 이유는 강의 뒷 부분에서 설명한다.
     */

}
