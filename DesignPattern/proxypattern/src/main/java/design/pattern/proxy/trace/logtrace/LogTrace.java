package design.pattern.proxy.trace.logtrace;

import design.pattern.proxy.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);
    
    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
    
}
