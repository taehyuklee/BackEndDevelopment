package design.pattern.proxy.config.v1_proxy.interface_proxy;

import design.pattern.proxy.app.version1.OrderServiceV1;
import design.pattern.proxy.trace.TraceStatus;
import design.pattern.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements OrderServiceV1{

    private final OrderServiceV1 target; //실제 객체를 주입 받을 인터페이스 
    private final LogTrace logTrace;

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        
        try{
            status = logTrace.begin("OrderRepository.request()");

            target.orderItem(itemId); //target (실제 객체)의 로직 interface

            logTrace.end(status);

        } catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
    
}
