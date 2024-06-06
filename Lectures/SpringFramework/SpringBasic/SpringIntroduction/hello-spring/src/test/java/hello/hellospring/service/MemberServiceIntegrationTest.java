package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//여태까지는 JAVA로 Test했다고 생각하면 되는데 과연 DB로 연결시킨다면?
@SpringBootTest
@Transactional //기능 하나 하나가 끝날때마다 Rollback을 해준다. (Test case에 붙었을때만 이럼)
class MemberServiceIntegrationTest {

    //회원 가입 Test할려면 우선 서비스가 있어야 겠지
    @Autowired MemberService memberService; // = new MemberService();
    //Test는 어디서 갖다쓰는게 아니기때문에 field기반 autowired받아도 됨

    //여기서도 Clear를 해줘야 한다 앞선 MemoeryMemberRepoTest와 마찬가지로 따라서 MemoryMemberRepository Class가져와야 한다
    @Autowired MemberRepository memberRepository; // = new MemoryMemberRepository();


    @Test
    void 회원가입() {
        //given (이런 상황이 주어졌을때)
        Member member = new Member(); //member를 하나 만들어보고
        member.setName("spring"); //member의 이름을 hello라고 해주고

        //when (이때 이런걸 실행했을때)
        Long saveId = memberService.join(member);

        //then (이런 결과가 나와야해)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when (예외가 터지는것까지 확인을 해줘야 한다)
        memberService.join(member1);
        //assertThrows(IllegalStateException.class, ()-> memberService.join(member2));
        //아래와 같이 Try catch로 터뜨려줘도 되긴 한다.
//        try{
//            memberService.join(member1);
//            fail();
//        }catch(IllegalAccessException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//
//        }

        //또는 IllegalStateException 객체로 받아서 그 객체를 검증하는 방법도 존재한다
        IllegalStateException e = assertThrows(IllegalStateException.class, ()-> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");


        //then
    }
}