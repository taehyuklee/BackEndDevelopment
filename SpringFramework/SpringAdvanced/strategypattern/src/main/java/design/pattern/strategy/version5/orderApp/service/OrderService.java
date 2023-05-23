package design.pattern.strategy.version5.orderApp.service;

import org.springframework.stereotype.Service;

import design.pattern.strategy.version5.orderApp.domain.repository.OrderRepository;
import design.pattern.strategy.version5.trace.logtrace.LogTrace;
import design.pattern.strategy.version5.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;

@Service("orderServiceV5")
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final LogTrace traceService;
    

    //Controller단에서 생성한 TraceId를 받아와야 한다.
    public void orderItem(String itemId){

        //이렇게 한줄로 끝남 (만들어 놓은 template에 넣어준다)
        AbstractTemplate<Void> template = new AbstractTemplate<Void>(traceService) {

            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
            
        };
        template.execute("OrderController.request()");

    }
    
}
