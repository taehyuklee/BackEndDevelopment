package spring.logtracing.trace.tracev1;

import org.junit.jupiter.api.Test;

import spring.logtracing.trace.TraceStatus;
import spring.logtracing.trace.TraceService.TraceV1;

public class traceTestV1 {
    
    @Test
    void begin_end() {
        TraceV1 trace = new TraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception(){
        TraceV1 trace = new TraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalAccessException());
    }
}
