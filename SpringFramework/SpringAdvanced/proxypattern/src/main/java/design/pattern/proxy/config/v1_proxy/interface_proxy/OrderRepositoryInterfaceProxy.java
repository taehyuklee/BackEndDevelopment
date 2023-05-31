package design.pattern.proxy.config.v1_proxy.interface_proxy;

import design.pattern.proxy.app.version1.OrderRepositoryV1;
import design.pattern.proxy.trace.TraceStatus;
import design.pattern.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryV1{

    private final OrderRepositoryV1 target; //실제 객체를 주입 받을 인터페이스 
    private final LogTrace logTrace;

    @Override
    public void save(String itemId) {
        
        TraceStatus status = null;
        
        try{
            status = logTrace.begin("OrderRepository.request()");

            target.save(itemId); //여긴 실제 객체

            logTrace.end(status);

        } catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
    
}
