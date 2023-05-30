package design.pattern.proxy.trace.template;

import design.pattern.proxy.trace.TraceStatus;
import design.pattern.proxy.trace.callback.TraceCallback;
import design.pattern.proxy.trace.logtrace.LogTrace;

public class TraceTemplate {

    private final LogTrace traceService;

    public TraceTemplate(LogTrace traceService){
        this.traceService = traceService;
    }

    /*
     * <T>: 제네릭 타입 매개변수 T를 선언합니다. 이는 메서드에서 사용되는 타입의 실제 값이 호출하는 쪽에서 전달될 수 있음을 나타냅니다.
     * T: 제네릭 타입 매개변수 T를 반환 타입으로 지정합니다. 이는 호출하는 쪽에서 전달된 실제 타입에 따라 반환 타입이 결정됩니다.
     */
    
    public <T> T execute(String message, TraceCallback<T> callback){
        TraceStatus status = null;

        try{
            //Logging 시작
            status = traceService.begin(message);

            //핵심 로직 호출 (로직에 따라 변하는 부분)
            T result = callback.call();

            //Logging 종료
            traceService.end(status);
            return result;

        }catch(Exception e){
            traceService.exception(status, e);
            throw e;
        }
    }
    
}
