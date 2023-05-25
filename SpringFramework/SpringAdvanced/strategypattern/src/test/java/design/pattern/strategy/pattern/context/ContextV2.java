package design.pattern.strategy.pattern.context;

import design.pattern.strategy.pattern.strategy.Strategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2 {

    //이번에는 attribute(field)로 받는 것이 아니라, Parameter로 전략을 받아오도록 해본다.
    public void execute(Strategy strategy){
        long startTime = System.currentTimeMillis();

        //핵심 비즈니스 로직 실행
        strategy.call(); //위임
        //핵심 비즈니스 로직 종료
        
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

}
