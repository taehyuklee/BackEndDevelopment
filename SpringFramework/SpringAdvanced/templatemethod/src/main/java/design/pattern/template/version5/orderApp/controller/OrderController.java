package design.pattern.template.version5.orderApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import design.pattern.template.version5.orderApp.service.OrderService;
import design.pattern.template.version5.trace.logtrace.LogTrace;
import design.pattern.template.version5.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;


@RestController("orderController5")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final LogTrace traceService;

    @GetMapping("/v5/request")
    public String request(String itemId){

        AbstractTemplate<String> template = new AbstractTemplate<String>(traceService) {

            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
            
        };

        return template.execute("OrderController.request()");
    }
    
}
