package design.pattern.proxy.config.v1_proxy.interface_proxy;

import design.pattern.proxy.app.version1.OrderControllerV1;
import design.pattern.proxy.trace.TraceStatus;
import design.pattern.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1{

    private final OrderControllerV1 target; //실제 객체를 주입 받을 인터페이스 
    private final LogTrace logTrace;

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        
        try{
            status = logTrace.begin("OrderController.request()");

            String result = target.request(itemId); //실제 target의 로직

            logTrace.end(status);

            return result;

        } catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
    
}
