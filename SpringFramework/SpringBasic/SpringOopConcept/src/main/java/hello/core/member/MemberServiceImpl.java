package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    //DAO를 바라보고 있어야 한다. 서비스를 하기 위해서는 DB에 접근할 DAO가 필요하니까
    /*private MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
    * 위와 같이 하면 Singleton법칙도 위배되고 DIP도 위배되는 것이다. Interface에 의존해야하는데, 구현체에 의존하니까*/

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {//회원가입
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {//회원 조회
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
