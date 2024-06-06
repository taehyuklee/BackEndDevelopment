package spring.aop.internalcall;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CallServiceV1 {

    //자기 자신을 주입받아서 내부 method를 주입받은(proxy)객체의 method로 사용하면 proxy를 사용할수 있다.
    private CallServiceV1 callServiceV1;

    //여기에 @Autowired로 생성자 주입받으면, 순환참조 문제가 생기게 된다. (그래서 생성이 다 끝나고 setter를 이용해서 주입해준다)
    // @Autowired
    // public void setCallServiceV1(CallServiceV1 callServiceV1){
    //     log.info("callServiceV1 setter={}", callServiceV1.getClass());
    //     this.callServiceV1 = callServiceV1;
    // }


    public void external(){
        log.info("call external");
        callServiceV1.internal(); //자기 자신을 받은 외부 메서드를 사용한다.
    }

    public void internal(){
        log.info("call ineternal");
    }

    //나는 자기 자신 수정자주입하는게 안되네 이것도 순환참조에 걸림

    /*
     * 
        Description:

        The dependencies of some of the beans in the application context form a cycle:

        ┌──->──┐
        |  callServiceV1
        └──<-──┘


        Action:

        Relying upon circular references is discouraged and they are prohibited by default. Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.
     * 
     */
    
}
