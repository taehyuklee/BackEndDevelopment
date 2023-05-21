package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member); //회원이 저장소에 저장이 됨
    Optional<Member> findById(Long id); //방금 아이디로 회원을 찾는
    //Optional은 자바 8에 들어간 기능
    Optional<Member> findByName(String name);
    List<Member> findAll(); //저장된 모든 리스트를 반환해온다

}
