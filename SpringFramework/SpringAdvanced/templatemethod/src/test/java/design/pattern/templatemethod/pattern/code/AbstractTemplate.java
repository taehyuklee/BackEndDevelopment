package design.pattern.templatemethod.pattern.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {
    
    //template 부분
    public void execute(){
        long startTime = System.currentTimeMillis();

        //핵심 비즈니스 로직 실행
        call(); //상속 (자식 class에 따라서 이 부분이 바뀐다)
        //핵심 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    //자식 class에서 call을 구현해서 넣어준다.
    protected abstract void call();
}
