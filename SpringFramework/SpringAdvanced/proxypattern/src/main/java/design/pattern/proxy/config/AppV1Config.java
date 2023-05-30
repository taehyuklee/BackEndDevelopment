package design.pattern.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import design.pattern.proxy.app.version1.OrderControllerV1;
import design.pattern.proxy.app.version1.OrderControllerV1Impl;
import design.pattern.proxy.app.version1.OrderRepositortyV1;
import design.pattern.proxy.app.version1.OrderRepositoryV1Imple;
import design.pattern.proxy.app.version1.OrderServiceV1;
import design.pattern.proxy.app.version1.OrderServiceV1Imple;

@Configuration
public class AppV1Config {

    //빈등록시 이름을 따로 명시해주지 않는 이상 method이름을 그대로 bean이름으로 가져간다. (orderControllerV1 과같이 method이름을 명명한 이유)
    @Bean
    public OrderControllerV1 orderControllerV1(){
        return new OrderControllerV1Impl(orderServiceV1());
    }

    @Bean
    public OrderServiceV1 orderServiceV1(){
        return new OrderServiceV1Imple(orderRepositortyV1());
    }

    @Bean
    public OrderRepositortyV1 orderRepositortyV1(){
        return new OrderRepositoryV1Imple();
    }

}
