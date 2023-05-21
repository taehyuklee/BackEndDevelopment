package spring.logtracing.version1.orderApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.logtracing.version1.orderApp.service.OrderService;
import spring.logtracing.version1.trace.TraceStatus;
import spring.logtracing.version1.trace.TraceService.TraceService;

@RestController("orderController1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final TraceService traceService;

    @GetMapping("/v1/request")
    public String request(String itemId){

        //logging logic with controller
        TraceStatus status = null;
        try{
            //TraceStatus로 logging시작.
            status = traceService.begin("OrderController.request()");

            orderService.orderItem(itemId);

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
