package design.pattern.proxy.app.version1;

public class OrderServiceV1Imple implements OrderServiceV1{

    private final OrderRepositortyV1 orderRepositorty;

    
    public OrderServiceV1Imple(OrderRepositortyV1 orderRepositorty){
        this.orderRepositorty = orderRepositorty;
    }


    @Override
    public void orderItem(String itemId) {
        orderRepositorty.save(itemId);
    }
    
}
