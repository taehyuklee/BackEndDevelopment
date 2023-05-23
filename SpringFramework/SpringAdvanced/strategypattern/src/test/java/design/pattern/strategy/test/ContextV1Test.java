package design.pattern.strategy.test;

import org.junit.jupiter.api.Test;

import design.pattern.strategy.pattern.StrategyLogic1;
import design.pattern.strategy.pattern.StrategyLogic2;
import design.pattern.strategy.pattern.context.ContextV1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV1Test {
    
    @Test
    void TemplateMethodV0(){
        logic1();
        logic2();
    }

    private void logic1(){
        long startTime = System.currentTimeMillis();
        //핵심 비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //핵심 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2(){
        long startTime = System.currentTimeMillis();
        //핵심 비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //핵심 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }


    /*
     * 전략 패턴 사용
     */
    @Test
    void strategyV1(){
        StrategyLogic1 StrategyLogic1 = new StrategyLogic1(); //구현체를 만들어서
        ContextV1 contextV1 = new ContextV1(StrategyLogic1); //위 구현체의 인터페이스를 가지고 있는 전략 템플릿(Context) 에 넣어준다.
        contextV1.execute(); //그리고 전략을 실행시켜준다.

        StrategyLogic2 StrategyLogic2 = new StrategyLogic2(); //구현체를 만들어서
        ContextV1 contextV2 = new ContextV1(StrategyLogic2); //위 구현체의 인터페이스를 가지고 있는 전략 템플릿(Context) 에 넣어준다.
        contextV2.execute(); //그리고 전략을 실행시켜준다.

    }



}
