package hello.core.member;

public interface MemberRepository {
    
    void save(Member member); //저장 기능
    
    Member findById(Long memberId); //회원의 Id로 찾는기능

}
