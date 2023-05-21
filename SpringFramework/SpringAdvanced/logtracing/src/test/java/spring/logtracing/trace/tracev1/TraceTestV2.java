package spring.logtracing.trace.tracev1;

import org.junit.jupiter.api.Test;

import spring.logtracing.version2.trace.TraceStatus;
import spring.logtracing.version2.trace.TraceService.TraceService;

public class TraceTestV2 {

    @Test
    void begin_end(){
        TraceService trace = new TraceService();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception(){
        TraceService trace = new TraceService();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        trace.exception(status2, new IllegalAccessException());
        trace.exception(status1, new IllegalAccessException());
    }
    
}
