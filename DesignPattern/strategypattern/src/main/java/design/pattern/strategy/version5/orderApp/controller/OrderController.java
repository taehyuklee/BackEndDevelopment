package design.pattern.strategy.version5.orderApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import design.pattern.strategy.version5.orderApp.service.OrderService;
import design.pattern.strategy.version5.trace.callback.TraceCallback;
import design.pattern.strategy.version5.trace.logtrace.LogTrace;
import design.pattern.strategy.version5.trace.template.TraceTemplate;


@RestController("orderController5")
public class OrderController {

    private final OrderService orderService;
    private final TraceTemplate template;

    //생성자 주입시에는 RequiredAllArgs가 필요가 없다.
    public OrderController(OrderService orderService, LogTrace trace){
        this.orderService = orderService;
        this.template = new TraceTemplate(trace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId){

        return template.execute("OrderController.request()", new TraceCallback<String>() {

            @Override
            public String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
            
        });

    }
    
}
