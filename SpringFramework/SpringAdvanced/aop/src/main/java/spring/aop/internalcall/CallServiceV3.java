package spring.aop.internalcall;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CallServiceV3 {

    //이렇게 구조 자체를 분리해버리는 경우
    private final InternalService internalService;

    public void external(){
        log.info("call external");
        internalService.internal(); //외부 메서드 호출
    }


}
