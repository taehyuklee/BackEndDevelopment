package spring.logtracing.trace.tracev;

import org.junit.jupiter.api.Test;

import spring.logtracing.version2.trace.TraceStatus;
import spring.logtracing.version2.trace.TraceService.TraceService;

public class TraceTestV2 {

    @Test
    void begin_end(){
        TraceService traceService = new TraceService();
        TraceStatus status1 = traceService.begin("hello1");
        TraceStatus status2 = traceService.beginSync(status1.getTraceId(), "hello2");
        traceService.end(status2);
        traceService.end(status1);
    }

    @Test
    void begin_exception(){
        TraceService traceService = new TraceService();
        TraceStatus status1 = traceService.begin("hello1");
        TraceStatus status2 = traceService.beginSync(status1.getTraceId(), "hello2");
        traceService.exception(status2, new IllegalAccessException());
        traceService.exception(status1, new IllegalAccessException());
    }
    
}
