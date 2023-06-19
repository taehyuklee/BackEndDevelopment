package spring.aop.internalcall;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CallServiceV2 {
    //지연조회 방법 -> 자기 자신을 메소드 호출할때 호출하게 된다 (생성자주입에서 순환참조가 걸릴 이유가 없다)
    // private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider){
        this.callServiceProvider = callServiceProvider;
    }

    public void external(){
        log.info("call external");
        // CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal(); //자기 자신을 받은 외부 메서드를 사용한다.
    }

    public void internal(){
        log.info("call ineternal");
    }

}
