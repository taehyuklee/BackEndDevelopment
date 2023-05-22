package spring.logtracing.version3.orderApp.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.logtracing.version3.orderApp.domain.repository.OrderRepository;
import spring.logtracing.version3.trace.TraceId;
import spring.logtracing.version3.trace.TraceStatus;
import spring.logtracing.version3.trace.TraceService.TraceService;

@Service("orderServiceV3")
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TraceService traceService;

    //Controller단에서 생성한 TraceId를 받아와야 한다.
    public void orderItem(TraceId traceId, String itemId){

        TraceStatus status = null;
        try{
            //앞서 받아온 traceId에 transaction ID는 그대로 하고 LEVEL만 1을 더해서 새로 객체를 생성해서 반환해준다.
            status = traceService.beginSync(traceId, "OrderController.request()");
            orderRepository.save(status.getTraceId(), itemId);
            traceService.end(status);
            
        }catch(Exception e){
            traceService.exception(status, e); //여기까지하면, 예외를 처리해버리고 interrupt를 터뜨리지 않는다 (정상작동함)
            throw e; //throw를 해줌으로써 interrupt를 터뜨려준다.
        }

    }
    
}
