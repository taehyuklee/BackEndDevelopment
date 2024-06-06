package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;


public class AppConfig {

    //생성자 주입이라 한다.
    public MemberService memberSerivce(){
        return new MemberServiceImpl(memberRepository()); //생성자를 통해 구현체를 주입한다.
    }

    //우리 앱에서는 MemberRepository는 MemoryMemberRepository를 쓸거야 (객체를 만들어서 return해준다)
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

    public OrderService orderSerivce(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy(); //Fix와 Rate를 바꾸기 위해서는 여기만 바꾸면 된다.
    }
}
