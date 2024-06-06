package design.pattern.proxy.config.v1_proxy.concrete_proxy;

import design.pattern.proxy.app.version2.OrderServiceV2;
import design.pattern.proxy.trace.TraceStatus;
import design.pattern.proxy.trace.logtrace.LogTrace;


public class OrderServiceConcreteProxy extends OrderServiceV2{

    private final OrderServiceV2 target; //상속 받았는데 굳이 이렇게 주입받는 이유는 뭐지? (상속받은건 그냥 다형성때문인건가?)
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }
    
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
