package design.pattern.strategy.test;

import org.junit.jupiter.api.Test;

import design.pattern.strategy.pattern.context.ContextV2;
import design.pattern.strategy.pattern.strategy.StrategyLogic1;
import design.pattern.strategy.pattern.strategy.StrategyLogic2;


public class ContextV2Test {

    /*
     * 전략패턴 - 선조립 후 실행이 아니라, 실행할때마다 전략을 전달해서 실행할수 있도록 한다.
     */
    @Test
    void strategyV1(){
        //이렇게 전략을 파라미터로 넘겨주는 것을 전략패턴을 "템플릿 콜백 패턴"이라 한다.
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }
    
}
