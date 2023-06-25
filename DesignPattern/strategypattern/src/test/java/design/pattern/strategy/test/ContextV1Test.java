package design.pattern.strategy.test;

import org.junit.jupiter.api.Test;

import design.pattern.strategy.pattern.context.ContextV1;
import design.pattern.strategy.pattern.strategy.Strategy;
import design.pattern.strategy.pattern.strategy.StrategyLogic1;
import design.pattern.strategy.pattern.strategy.StrategyLogic2;
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

    /*
     * 여기는 위와다른게 구현체를 만드는게 아니라, 익명 클래스를 사용해서 바로 만드는 것 -> 익명 클래스로 만든 것을 템플릿 코드(Context)에 넣어줘서 실행시킨다
     */
    @Test
    void strategyV2(){
        Strategy strategyLogic1 = new Strategy(){

            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
            
        };
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        Strategy strategyLogic2 = new Strategy(){

            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
            
        };
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();


        //Inline으로 다음과 같이 할수도 있다
        ContextV1 context3 = new ContextV1(new Strategy(){
                @Override
                public void call() {
                    log.info("비즈니스 로직3 실행");
                }  
        });
        context3.execute();


        //Lambda식 쓰는 법 (인터페이스에 메서드가 1개만 있어야 한다)
        ContextV1 context4 = new ContextV1(()->log.info("비즈니스 로직4 실행"));
        context4.execute();
    }

}
