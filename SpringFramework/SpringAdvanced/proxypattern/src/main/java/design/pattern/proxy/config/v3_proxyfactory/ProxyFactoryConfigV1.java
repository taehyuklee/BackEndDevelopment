package design.pattern.proxy.config.v3_proxyfactory;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import design.pattern.proxy.app.version1.OrderControllerV1;
import design.pattern.proxy.app.version1.OrderControllerV1Impl;
import design.pattern.proxy.app.version1.OrderRepositoryV1;
import design.pattern.proxy.app.version1.OrderRepositoryV1Impl;
import design.pattern.proxy.app.version1.OrderServiceV1;
import design.pattern.proxy.app.version1.OrderServiceV1Impl;
import design.pattern.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import design.pattern.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace){
        OrderControllerV1 orderController = new OrderControllerV1Impl(orderServiceV1(logTrace)); // proxy를 넣어줘야 한다

        ProxyFactory factory = new ProxyFactory(orderController);
        factory.addAdvisor(getAdviosr(logTrace));
        OrderControllerV1 proxy = (OrderControllerV1) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderController.getClass());
        return proxy;
    }



    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace){
        OrderServiceV1 orderService = new OrderServiceV1Impl(orderRepositoryV1(logTrace)); // proxy를 넣어줘야 한다

        ProxyFactory factory = new ProxyFactory(orderService);
        factory.addAdvisor(getAdviosr(logTrace));
        OrderServiceV1 proxy = (OrderServiceV1) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderService.getClass());
        return proxy;
    }


    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace){
        OrderRepositoryV1Impl orderRepository = new OrderRepositoryV1Impl();

        ProxyFactory factory = new ProxyFactory(orderRepository);
        factory.addAdvisor(getAdviosr(logTrace));
        OrderRepositoryV1 proxy = (OrderRepositoryV1) factory.getProxy();
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
