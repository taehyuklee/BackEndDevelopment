package spring.logtracing.version3.orderApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.logtracing.version2.orderApp.service.OrderService;
import spring.logtracing.version2.trace.TraceStatus;
import spring.logtracing.version2.trace.TraceService.TraceService;


@RestController("orderController3")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final TraceService traceService;

    @GetMapping("/v3/request")
    public String request(String itemId){
        //logging logic with controller
        TraceStatus status = null;
        try{
            //TraceStatus로 logging시작.
            status = traceService.begin("OrderController.request()");

            //동기화를 위해 생성된 Controller단에서 생성된 traceId를 뒤로 넘겨준다.
            orderService.orderItem(status.getTraceId(), itemId);

            //TraceStatus로 logging 마무리.
            traceService.end(status);

            return "ok";

        }catch (Exception e){
            //logging시 예외처리
            traceService.exception(status, e);
            throw e;
        }
    }
    
}
