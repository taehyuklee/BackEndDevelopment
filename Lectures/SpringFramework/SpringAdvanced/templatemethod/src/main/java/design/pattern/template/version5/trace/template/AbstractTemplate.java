package design.pattern.template.version5.trace.template;

import design.pattern.template.version5.trace.TraceStatus;
import design.pattern.template.version5.trace.logtrace.LogTrace;


//여기에 앞선 try catch로 logging 부분 넣었던 것에 대해 Template을 만든다.
public abstract class AbstractTemplate<T> {

    private final LogTrace traceService;

    public AbstractTemplate(LogTrace trace){
        this.traceService = trace;
    }

    //Logging Template
    public T execute(String message){
        TraceStatus status = null;

        try{
            //Logging 시작
            status = traceService.begin(message);

            //핵심 로직 호출 (로직에 따라 변하는 부분)
            T result = call();

            //Logging 종료
            traceService.end(status);
            return result;

        }catch(Exception e){
            traceService.exception(status, e);
            throw e;
        }

    }

    protected abstract T call();
    
}
