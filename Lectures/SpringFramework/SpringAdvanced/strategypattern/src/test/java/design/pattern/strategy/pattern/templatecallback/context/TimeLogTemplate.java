package design.pattern.strategy.pattern.templatecallback.context;

import design.pattern.strategy.pattern.templatecallback.strategy.Callback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeLogTemplate {
    
    public void execute(Callback callback){
        long startTime = System.currentTimeMillis();

        callback.call(); //지금 TimeLogTemplate(Back)에서 Callback함수를 call한다. (callback의 어원)

        long endTime = System.currentTimeMillis();

        long rsltTime = endTime - startTime;
        log.info("resultTime={}", rsltTime);
    }
}
