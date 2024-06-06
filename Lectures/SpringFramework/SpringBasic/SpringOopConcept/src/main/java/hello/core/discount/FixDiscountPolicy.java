package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component
public class FixDiscountPolicy implements DiscountPolicy{
    
    private int discountFixAmount = 1000; //1000원 할인

    @Override
    public int discount(Member member, int price) {
        
        if(member.getGrade() == Grade.VIP){ 
            //Enum type은 ==쓰는게 맞다. .equals()는 객체일때 쓰이게 되고
            return discountFixAmount;
        } else{
            return 0;
        }
    }
}
