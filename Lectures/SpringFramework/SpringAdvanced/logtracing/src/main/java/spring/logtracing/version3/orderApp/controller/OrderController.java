package spring.logtracing.version3.orderApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.logtracing.version3.orderApp.service.OrderService;
import spring.logtracing.version3.trace.TraceStatus;
import spring.logtracing.version3.trace.logtrace.LogTrace;


@RestController("orderController3")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final LogTrace traceService;

    @GetMapping("/v3/request")
    public String request(String itemId){
        //logging logic with controller
        TraceStatus status = null;
        try{
            //TraceStatus로 logging시작.
            status = traceService.begin("OrderController.request()");

            //파라미터 다 없애준다. 어차피 Singleton으로 관리되니까
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
