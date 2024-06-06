package design.pattern.proxy.config.v2_dynamicproxy.handler;

import java.lang.reflect.Proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import design.pattern.proxy.app.version1.OrderControllerV1;
import design.pattern.proxy.app.version1.OrderControllerV1Impl;
import design.pattern.proxy.app.version1.OrderRepositoryV1;
import design.pattern.proxy.app.version1.OrderRepositoryV1Impl;
import design.pattern.proxy.app.version1.OrderServiceV1;
import design.pattern.proxy.app.version1.OrderServiceV1Impl;
import design.pattern.proxy.trace.logtrace.LogTrace;

@Configuration
public class DynamicProxyBasicConfig {

    //new Class[]{OrderServiceV1.class} 자바에서 배열 생성하는거 int[]{1,2,3} 이런식으로 하지 참.

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace){
        OrderControllerV1 target = new OrderControllerV1Impl(orderServiceV1(logTrace));
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(), new Class[]{OrderControllerV1.class}, handler); //proxy를 반환해준다
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace){
        OrderServiceV1 target = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(), new Class[]{OrderServiceV1.class}, handler); //proxy를 반환해준다
    }
    
    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace){
        OrderRepositoryV1 target = new OrderRepositoryV1Impl();
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(), new Class[]{OrderRepositoryV1.class}, handler); //proxy를 반환해준다
    }

    
}

