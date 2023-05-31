package design.pattern.proxy.pureproxy.decorator;

import org.junit.jupiter.api.Test;

import design.pattern.proxy.pureproxy.decorator.code.Component;
import design.pattern.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import design.pattern.proxy.pureproxy.decorator.code.MessageDecorator;
import design.pattern.proxy.pureproxy.decorator.code.RealComponent;
import design.pattern.proxy.pureproxy.decorator.code.TimeDecorator;

public class DecoratorPatternTest {
    
    @Test
    void noDecorator(){
        Component realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
    }


    @Test
    void decorator1(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator); //Proxy객체인 messageDecorator를 가지고 있어야 한다.
        client.execute();
    }
    //코드를 수정하지 않고 로그를 남기는거 -> Client와 Target객체는 아무 변화가 없다. 하지만 프록시 패턴을 사용해서 중간에 로그를 남기거나 꾸미는게 가능해졌다.

    //여러개의 Decorator proxy객체를 이용해서 꾸며볼 것이다.
    @Test
    void decorator2(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator); //timeDecorator가 messageDecorator를 부르고 messageDecorator가 Target을 부른다. 즉 두 번 꾸미게 됨.
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator); 
        client.execute();
    }

}
