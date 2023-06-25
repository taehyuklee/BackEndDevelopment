package design.pattern.proxy.config.v1_proxy.concrete_proxy;

import design.pattern.proxy.app.version2.OrderRepositoryV2;
import design.pattern.proxy.trace.TraceStatus;
import design.pattern.proxy.trace.logtrace.LogTrace;

public class OrderRepositoryConcreteProxy extends OrderRepositoryV2{

    private final OrderRepositoryV2 target; //상속 받았는데 굳이 이렇게 주입받는 이유는 뭐지?
    private final LogTrace logTrace;

    public OrderRepositoryConcreteProxy(OrderRepositoryV2 target, LogTrace logTrace){
        this.target = target;
        this.logTrace = logTrace;
    }

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
