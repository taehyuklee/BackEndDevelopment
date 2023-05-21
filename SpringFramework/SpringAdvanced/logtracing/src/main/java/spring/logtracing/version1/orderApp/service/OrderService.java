package spring.logtracing.version1.orderApp.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.logtracing.version1.orderApp.domain.repository.OrderRepository;
import spring.logtracing.version1.trace.TraceStatus;
import spring.logtracing.version1.trace.TraceService.TraceService;

@Service("orderServiceV1")
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TraceService tracesService;

    public void orderItem(String itemId){

        //아래 세줄이면 참 간단하겠는데, 실상은 예외처리 try catch때문에 그렇지 않다.
        /* TraceStatus status = trace.begin("OrderController.request()");
        *  orderRepository.save(itemId);
        *  trace.end(status); */

        TraceStatus status = null;
        try{
            status = tracesService.begin("OrderController.request()");
            orderRepository.save(itemId);
            tracesService.end(status);
            
        }catch(Exception e){
            tracesService.exception(status, e); //여기까지하면, 예외를 처리해버리고 interrupt를 터뜨리지 않는다 (정상작동함)
            throw e; //throw를 해줌으로써 interrupt를 터뜨려준다.
        }

    }
    
}
