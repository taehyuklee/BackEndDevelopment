package orm.jpashop.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orm.jpashop.domain.Order;
import orm.jpashop.domain.OrderItem;

@Service
@RequiredArgsConstructor
//@NoArgsConstructor //No Args랑 RquiredArgs 충돌함 <- 이거 확실히 알아야할듯
public class MemberService {
    
    // Spring Data JPA에서는 생성자 주입해 놓은 상태로 em을써야함 굳이 쓸거면
    private final EntityManager em;

    @Transactional
    public String orderService(){
        // One에 many들을 Logic에서 넣고 그냥 insert하면 다 들어가는 것인가? 단방향 Mapping되어 있을때 (이게 편하지 않나?) 
        // 여기서 살짝 괴리감이 있긴하네 보통 개발에서는 편하게 MongoDB형식으로 하면 저렇게 되는데
        // rdb에서는 Order하나 넣어놓고 다음 OrderItem들을 넣네. Table설계에 있어서 중요한 부분이다.
        Order order = new Order();
        order.addOrderItem(new OrderItem());

        return "Success";

    }

}
