package spring.logtracing.version2.orderApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.logtracing.version2.orderApp.service.OrderService;


@RestController("orderController2")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/v2/request")
    public String request(String itemId){
        orderService.orderItem(itemId);
        return "ok";
    }
    
}
