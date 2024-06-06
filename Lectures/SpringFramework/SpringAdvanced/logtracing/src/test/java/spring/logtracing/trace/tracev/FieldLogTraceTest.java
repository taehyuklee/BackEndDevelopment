package spring.logtracing.trace.tracev;

import org.junit.jupiter.api.Test;

import spring.logtracing.version3.trace.TraceStatus;
import spring.logtracing.version3.trace.logtrace.FieldLogTrace;

public class FieldLogTraceTest {

    FieldLogTrace trace = new FieldLogTrace();

    @Test
    void begin_end_level2(){
        //위에서 trace는 하나만으로 관리되니까
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception_level2(){
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.exception(status2, new IllegalAccessException());
        trace.exception(status1, new IllegalAccessException());
    }

}
