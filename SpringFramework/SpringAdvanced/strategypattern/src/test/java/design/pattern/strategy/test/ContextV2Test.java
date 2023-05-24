package design.pattern.strategy.test;

import org.junit.jupiter.api.Test;

import design.pattern.strategy.pattern.StrategyLogic1;
import design.pattern.strategy.pattern.StrategyLogic2;
import design.pattern.strategy.pattern.context.ContextV2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2Test {

    /*
     * 전략패턴 - 선조립 후 실행이 아니라, 실행할때마다 전략을 전달해서 실행할수 있도록 한다.
     */
    @Test
    void strategyV1(){
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }
    
}
