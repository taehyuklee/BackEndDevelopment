package design.pattern.proxy.config.v3_proxyfactory;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import design.pattern.proxy.app.version2.OrderControllerV2;
import design.pattern.proxy.app.version2.OrderRepositoryV2;
import design.pattern.proxy.app.version2.OrderServiceV2;
import design.pattern.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import design.pattern.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace){
        OrderControllerV2 orderController = new OrderControllerV2(orderServiceV2(logTrace)); // proxy를 넣어줘야 한다

        ProxyFactory factory = new ProxyFactory(orderController);
        factory.addAdvisor(getAdviosr(logTrace));
        OrderControllerV2 proxy = (OrderControllerV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderController.getClass());
        return proxy;
    }



    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace){
        OrderServiceV2 orderService = new OrderServiceV2(orderRepositoryV2(logTrace)); // proxy를 넣어줘야 한다

        ProxyFactory factory = new ProxyFactory(orderService);
        factory.addAdvisor(getAdviosr(logTrace));
        OrderServiceV2 proxy = (OrderServiceV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderService.getClass());
        return proxy;
    }


    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace){
        OrderRepositoryV2 orderRepository = new OrderRepositoryV2();

        ProxyFactory factory = new ProxyFactory(orderRepository);
        factory.addAdvisor(getAdviosr(logTrace));
        OrderRepositoryV2 proxy = (OrderRepositoryV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderRepository.getClass());
        return proxy;
    }


    private Advisor getAdviosr(LogTrace logTrace){
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);

    }
    
}
