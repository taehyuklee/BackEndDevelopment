package design.pattern.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import design.pattern.proxy.app.version2.OrderControllerV2;
import design.pattern.proxy.app.version2.OrderRepositoryV2;
import design.pattern.proxy.app.version2.OrderServiceV2;

@Configuration
public class AppV2Config {
    
    @Bean
    public OrderControllerV2 orderControllerV2(){
        return new OrderControllerV2(orderServiceV2());
    }

    @Bean
    public OrderServiceV2 orderServiceV2(){
        return new OrderServiceV2(orderRepositoryV2());
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(){
        return new OrderRepositoryV2();
    }
}
