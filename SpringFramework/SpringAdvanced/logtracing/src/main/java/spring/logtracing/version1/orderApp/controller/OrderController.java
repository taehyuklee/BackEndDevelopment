package spring.logtracing.version1.orderApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.logtracing.version1.orderApp.service.OrderService;

@RestController("orderController1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/v1/request")
    public String request(String itemId){
        orderService.orderItem(itemId);
        return "ok";
    }
    
}
