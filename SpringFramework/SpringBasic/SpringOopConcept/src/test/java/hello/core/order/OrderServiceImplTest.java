package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void createOrder(){

        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1l, "itemA",10000);
        //여기서 내가 그냥 OrderServiceImpl만 test해보고 싶어도 그 안에 있는 Repository가
        //주입되지 않아서 NullpointerException error가 터진다. 가짜 Repo라도 넣어줘야 한다.

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(10000);
    }

}