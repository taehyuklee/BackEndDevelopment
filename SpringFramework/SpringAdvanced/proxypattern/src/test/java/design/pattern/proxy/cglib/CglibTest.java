package design.pattern.proxy.cglib;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

import design.pattern.proxy.cglib.code.TimeMethodInterceptor;
import design.pattern.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CglibTest {
    

    @Test
    void cglib(){
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcreteService proxy = (ConcreteService) enhancer.create(); //Service 구현체도 위와같이 Proxy로 Code generation Library를 이용해서 가능하다.

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        //이렇게 Proxy를 쓸수 있다.
        proxy.call();
    }

    //cglib은 제약이 몇 가지 존재한다 -> 구현체에 대해 만들기때문에 기본생성자와 getter, setter등과 같은 것들이 있어야하고, final keyword가 있으면 오버라이딩이나 상속받지 못한다.
    //프록시 팩토리를 이용하면 좀 더 편하게 적용가능하다 (위의 단점을 극복할수 있다)
}
