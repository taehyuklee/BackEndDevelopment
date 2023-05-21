package spring.logtracing.trace.tracev1;

import org.junit.jupiter.api.Test;

import spring.logtracing.version1.trace.TraceStatus;
import spring.logtracing.version1.trace.TraceService.TraceService;



public class TraceTestV1 {
    
    @Test
    void begin_end() {
        TraceService trace = new TraceService();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception(){
        TraceService trace = new TraceService();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalAccessException());
    }
}
