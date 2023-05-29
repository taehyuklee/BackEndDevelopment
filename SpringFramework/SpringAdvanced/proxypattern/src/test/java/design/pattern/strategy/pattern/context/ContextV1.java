package design.pattern.strategy.pattern.context;

import design.pattern.strategy.pattern.strategy.Strategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV1 {

    private Strategy strategy;

    public ContextV1(Strategy strategy){
        this.strategy = strategy;
    }

    public void execute(){
        long startTime = System.currentTimeMillis();

        //핵심 비즈니스 로직 실행
        strategy.call(); //위임
        //핵심 비즈니스 로직 종료
        
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
    
}
