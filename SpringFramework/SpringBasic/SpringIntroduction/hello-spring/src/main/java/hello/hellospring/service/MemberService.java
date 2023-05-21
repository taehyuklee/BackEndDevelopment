package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    //Test 껍데기를 만들고 싶다면 MemberService에서 Ctrl + shift + T를 해서 Test해라

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //회원 서비스를 만들기 위해서는 회원 Repository가 있어야 한다.
    //회원마다 맞춤형 서비스를 해줘야 하니까
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /*회원 가입*/
    public Long join(Member member){

        //long start = System.currentTimeMillis();

        /*
        //비즈니스 로직중 같은 이름이 있는 회원은 중복 가입이 안된다를 넣어볼 것이다.
        //같은 이름이 있는 중복 회원 X
        Optional<Member> result = memberRepository.findByName(member.getName());
        //Optional class가 도대체 뭘까? (대충 어떻게 생각하면 되냐?///////////////////////////////////////////////////////////////////////////////////-
        //Optional로 감싸면 Optional틀 안에 member가 들어 있어서 Optional의 메소드를 사용할수 있다는 장점이 있다.
        //옛날에는 if null이면 if, else 등으로 코딩했겠지만, 지금은 Optional로 감싸서
        //Optional에서 꺼내고싶으면 get으로 꺼내면 된다 -> class type이 꺼내지겠지? 그러면 Member로 받아올수 있다.
        //Memeber result = .get() 이런식이 됨.

        result.ifPresent(m ->{
            try {
                throw new IllegalAccessException("이미 존재하는 회원입니다.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
         위에처럼 코드를 짤수도 있지만 Optional을 감싸는 거은 그닥 좋은 것은 아니라네 그래서 아래와 같이 짠다고 함*/
        try{
            validateDuplicateMember(member);//중복 회원 검증
            memberRepository.save(member);
            return member.getId();}
        finally{
            //long finish = System.currentTimeMillis();
            //long timeMs = finish - start;
            //System.out.println("join=" + timeMs + "ms");
        }

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) //어차피 Optional이 반환되므로 바로 Optional에 관한 method를 
                .ifPresent(m ->{
                        throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

    //전체 회원 검색하는 기능
    public List<Member> findMembers(){
        return memberRepository.findAll();
        //findAll 반환타입 어차피 List니까
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

    //자 여기서 이제 회원서비스 테스트를 해보도록 하자.

}
