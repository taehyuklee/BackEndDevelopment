package design.pattern.template.version5.trace.logtrace;

import design.pattern.template.version5.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);
    
    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
    
}
