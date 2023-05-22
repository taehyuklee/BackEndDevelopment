package spring.logtracing.version3.trace.logtrace;

import spring.logtracing.version3.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);
    
    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
    
}
