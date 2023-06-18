package spring.aop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import spring.aop.order.OrderRepository;
import spring.aop.order.OrderService;
import spring.aop.order.aop.AspectV5Order;
import spring.aop.order.aop.AspectV6Advice;

@Slf4j
@SpringBootTest
// @Import(AspectV1.class) //Import하면 Spring Baen으로 자동 등록된다.
// @Import(AspectV2.class)
// @Import(AspectV4Pointcut.class)
// @Import({AspectV5Order.LogAspect.class, AspectV5Order.TxAspect.class})
@Import(AspectV6Advice.class)
public class AopTest {
    
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test //Aop가 적용이 되었는지 되어있지 않은지에 대한 확인을 함 - AopUtils.isAopProxy 이 method로 알아볼수 있다.
    void aopInfo(){
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));  
    }

    @Test
    void success(){
        orderService.orderItem("itemA");
    }

    @Test
    void exception(){
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }

}
