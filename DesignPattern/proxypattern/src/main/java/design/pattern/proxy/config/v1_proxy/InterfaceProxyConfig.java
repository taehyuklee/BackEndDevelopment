package design.pattern.proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import design.pattern.proxy.app.version1.OrderControllerV1;
import design.pattern.proxy.app.version1.OrderControllerV1Impl;
import design.pattern.proxy.app.version1.OrderRepositoryV1;
import design.pattern.proxy.app.version1.OrderRepositoryV1Impl;
import design.pattern.proxy.app.version1.OrderServiceV1;
import design.pattern.proxy.app.version1.OrderServiceV1Impl;
import design.pattern.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import design.pattern.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import design.pattern.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import design.pattern.proxy.trace.logtrace.LogTrace;


//앞서 AppConfig에서는 구현체를 bean에 직접 등록했지만, 여기서는 Proxy를 중간에 끼어 넣을 것이다.
@Configuration
public class InterfaceProxyConfig {

    //사실상 모두 Proxy를 넣어줘야 log를 찍고 반환해준다.

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) { //Proxy를 반환해야한다
        OrderControllerV1Impl target = new OrderControllerV1Impl(orderService(logTrace)); //proxy에 넣어줄 실제 target 구현체
        return new OrderControllerInterfaceProxy(target, logTrace); //여기서 Impl에서도 Proxy를 호출해줘야 한다.
    }


    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl target = new OrderServiceV1Impl(orderRepository(logTrace)); //proxy에 넣어줄 실제 target 구현체
        return new OrderServiceInterfaceProxy(target, logTrace); //proxy를 넣어준다
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl target = new OrderRepositoryV1Impl(); //proxy에 넣어줄 실제 target 구현체
        return new OrderRepositoryInterfaceProxy(target, logTrace); //proxy를 넣어준다
    }

}