package design.pattern.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

//Proxy객체 역할을 해준다
@Slf4j
public class MessageDecorator implements Component{

    private Component component;

    public MessageDecorator(Component component){
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");
        String realData = component.operation();
        String decoResult = "******" + realData + "******";
        log.info("MessageDecorator 꾸미기 적용 전={}, 적용 후={}", realData, decoResult);
        return decoResult;
    }
    
}
