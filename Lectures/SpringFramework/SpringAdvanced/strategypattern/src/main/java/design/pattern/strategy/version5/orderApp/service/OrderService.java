package design.pattern.strategy.version5.orderApp.service;

import org.springframework.stereotype.Service;

import design.pattern.strategy.version5.orderApp.domain.repository.OrderRepository;
import design.pattern.strategy.version5.trace.callback.TraceCallback;
import design.pattern.strategy.version5.trace.logtrace.LogTrace;
import design.pattern.strategy.version5.trace.template.TraceTemplate;

@Service("orderServiceV5")
public class OrderService {

    private final OrderRepository orderRepository;
    private final TraceTemplate template;

    public OrderService(OrderRepository orderRepository, LogTrace trace){
        this.template = new TraceTemplate(trace);
        this.orderRepository = orderRepository;

    }
    

    //Controller단에서 생성한 TraceId를 받아와야 한다.
    public Void orderItem(String itemId){
        return template.execute("OrderController.request()", new TraceCallback<Void>() {

            @Override
            public Void call() {
                orderRepository.save(itemId);
                return null;
            }
            
        });
        

    }
    
}
