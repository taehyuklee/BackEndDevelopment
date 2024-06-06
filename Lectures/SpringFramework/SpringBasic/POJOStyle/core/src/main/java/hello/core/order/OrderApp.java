package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.*;

public class OrderApp {

    public static void main(String[] args) {

        AppConfig appConfig = new AppConfig();

        MemberService memberService = appConfig.memberSerivce();
        MemberRepository memberRepository = new MemoryMemberRepository();
        OrderService orderService = appConfig.orderSerivce(); //구현체의 객체를 반환해준다

        //Test순서 -> 회원 등록 -> 등록된 회원이 Order했다고 하고 -> OrderService로 member와 같이 넘겨서 Test
        Member member = new Member(1L, "memberA", Grade.VIP);

        // 회원을 등록하고
        memberService.join(member);

        //DB에 있는 회원을 조회
        Member member1 = memberRepository.findById(1L);

        //등록된 회원이 Order하고 (Order객체가 생성된다)
        Order order = orderService.createOrder(member1.getId(), "itemA", 10000);

        //toString에 있는거 출력이 됨 객체 {}로 해서 출력되는거 확인.
        System.out.println("order = " + order);
        //System.out.println("order.calculaterPrice() = " + order.calculaterPrice());
        //order = Order{memberId=1, itemName='itemA', itemPrice=10000, discountPrice=1000}

    }
}
