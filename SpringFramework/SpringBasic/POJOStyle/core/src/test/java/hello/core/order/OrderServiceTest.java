package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    //test전에 무조건 돌리는거 BeforeEach
    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        this.memberService = appConfig.memberSerivce(); //객체를 만들어서 넣어준다.
        this.orderService = appConfig.orderSerivce(); //객체를 만들어서 넣어준다.
    }

    @Test
    public void createOrder(){

        Long memberId = 1L;

        //given
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);
        Member member1 = memberService.findMember(memberId);

        //when
        Order order = orderService.createOrder(member1.getId(),"ItemA",10000);

        //then
        Assertions.assertEquals(order.getDiscountPrice(), 1000);
    }
    //@Test에 createOrder() 안에 Parameter를 넣고 싶을때는 어떻게 해야하나?

}
