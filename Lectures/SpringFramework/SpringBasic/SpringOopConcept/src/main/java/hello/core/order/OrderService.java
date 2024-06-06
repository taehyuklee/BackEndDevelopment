package hello.core.order;

public interface OrderService {
    public Order createOrder(Long memberId, String itemName, int itemPrice);
    //createOrder에 위의 memberId, itemName, itemPrice를 넣으면 Order객체를 만들어서 반환해준다.
}
