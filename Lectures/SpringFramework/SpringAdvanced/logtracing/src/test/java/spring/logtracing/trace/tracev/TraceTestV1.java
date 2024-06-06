package spring.logtracing.trace.tracev;

import org.junit.jupiter.api.Test;

import spring.logtracing.version1.trace.TraceStatus;
import spring.logtracing.version1.trace.TraceService.TraceService;



public class TraceTestV1 {
    
    @Test
    void begin_end() {
        TraceService traceService = new TraceService();
        TraceStatus status = traceService.begin("hello");
        traceService.end(status);
    }

    @Test
    void begin_exception(){
        TraceService traceService = new TraceService();
        TraceStatus status = traceService.begin("hello");
        traceService.exception(status, new IllegalAccessException());
    }
}
