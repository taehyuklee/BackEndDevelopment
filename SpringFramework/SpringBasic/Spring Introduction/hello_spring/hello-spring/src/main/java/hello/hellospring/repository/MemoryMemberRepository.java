package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository //앞선 Service와 마찬가지로 Spring이 이것이 레포인것을 알수 있게 해줘야 한다.
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>(); //Member class Instance를 stor로 만든다.
    //Map -> egoing 듣기
    private static long sequence = 0L;

    @Override
    public Member save(Member member) { //이름은 save하기 전에 넘어온 상태이다.
        member.setId(++sequence); // sequence값을 왜 올리지?
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //store에서 꺼내서 반환해주면 된다
        //그런데 만약 id가 없으면 null이 반환된다면? Nullable로 감싸서 처리할수 있다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        Optional ret = store.values().stream().filter(member -> member.getName()
                        .equals(name)).findAny(); //filter egoing 듣기 findAny결과가 알아서 Optional로 반환이 된다.
        //return Optional.empty();
        return ret;
    }

    @Override
    public List<Member> findAll() { //list로 많이 쓴다.
        return new ArrayList<>(store.values());
    }

    //싹다 통을 비우게 해주는 것.
    public void clearStore(){
        store.clear();
    }
}

