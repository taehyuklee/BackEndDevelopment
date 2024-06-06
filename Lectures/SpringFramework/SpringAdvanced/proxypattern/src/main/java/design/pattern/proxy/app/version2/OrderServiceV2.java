package design.pattern.proxy.app.version2;

public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepositorty;
    
    public OrderServiceV2(OrderRepositoryV2 orderRepositorty){
        this.orderRepositorty = orderRepositorty;
    }
    
    public void orderItem(String itemId) {
        orderRepositorty.save(itemId);
    }
    
}
