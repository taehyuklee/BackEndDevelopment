package design.pattern.proxy.app.version1;

public class OrderServiceV1Impl implements OrderServiceV1{

    private final OrderRepositoryV1 orderRepositorty;

    
    public OrderServiceV1Impl(OrderRepositoryV1 orderRepositorty){
        this.orderRepositorty = orderRepositorty;
    }


    @Override
    public void orderItem(String itemId) {
        orderRepositorty.save(itemId);
    }
    
}
