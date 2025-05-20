package orm.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 주문서를 중심으로 어떤 아이템들이 있는지는 필요하기도 함 이제서야 양방향 Mapping이 되는거임.
    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems = new ArrayList<>();
    
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 편의성 메서드
    public void addOrderItem(OrderItem orderItem) {
        //원래는 JPA에서 영속성에 있다가 commit을하면 DB에 들어갈때는 알아서 관계가 Mapping이 되지만, 명시적으로 써주는게 좋다.
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

}
