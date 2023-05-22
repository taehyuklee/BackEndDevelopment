package spring.logtracing.version4.trace.logtrace;

import spring.logtracing.version4.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);
    
    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
    
}
