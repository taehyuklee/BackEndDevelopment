package design.pattern.proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import design.pattern.proxy.app.version2.OrderControllerV2;
import design.pattern.proxy.app.version2.OrderRepositoryV2;
import design.pattern.proxy.app.version2.OrderServiceV2;
import design.pattern.proxy.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import design.pattern.proxy.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import design.pattern.proxy.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import design.pattern.proxy.trace.logtrace.LogTrace;

@Configuration
public class ConcreteProxyConfig {
    
    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace){
        OrderControllerV2 controllerImpl = new OrderControllerV2(OrderServiceV2(logTrace));
        return new OrderControllerConcreteProxy(controllerImpl, logTrace);
    }

    @Bean
    public OrderServiceV2 OrderServiceV2(LogTrace logTrace){
        OrderServiceV2 serviceImpl = new OrderServiceV2(orderRepositoryV2(logTrace));
        return new OrderServiceConcreteProxy(serviceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace){
        OrderRepositoryV2 repositoryImple = new OrderRepositoryV2(); //여기서 생성해서 주입해주네 
        return new OrderRepositoryConcreteProxy(repositoryImple, logTrace); //프록시 구현체 객체를 등록해준다.
    }

}
