package design.pattern.proxy.app.version3;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepositorty;
    
    public OrderServiceV3(OrderRepositoryV3 orderRepositorty){
        this.orderRepositorty = orderRepositorty;
    }
    
    public void orderItem(String itemId) {
        orderRepositorty.save(itemId);

    }
}
