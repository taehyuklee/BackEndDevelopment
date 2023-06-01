package design.pattern.proxy.config.v1_proxy.concrete_proxy;

import design.pattern.proxy.app.version2.OrderControllerV2;
import design.pattern.proxy.trace.TraceStatus;
import design.pattern.proxy.trace.logtrace.LogTrace;

public class OrderControllerConcreteProxy extends OrderControllerV2 {

    private final OrderControllerV2 target; //상속 받았는데 굳이 이렇게 주입받는 이유는 뭐지?
    private final LogTrace logTrace;
    
    public OrderControllerConcreteProxy(OrderControllerV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
      
    }

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
