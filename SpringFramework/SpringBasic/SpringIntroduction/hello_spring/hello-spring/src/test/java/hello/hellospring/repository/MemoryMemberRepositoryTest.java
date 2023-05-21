package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Assertions; // 위의 assertj와 같이 import가 안되네
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //Test 끝나면 repository를 깔끔하게 지워주는 것을 만들어 줘야한다
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }//Test는 method들 사이에 서로 의존관계없이 설계가 되어야 하므로 하나의 test가 끝나면 비워줘야 한다.

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //Optional에서 값을 뺄때는 get으로 해서 뺄수 있다.

        //Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result);
        //static import하는 법 -> 윈도우에서는 alt+enter해서 demand on static import하면 static으로 들어가게 된다.
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); //shift F6
        member2.setName("spring2");
        repository.save(member2);

        //Member result = repository.findById(member.getId()).get();
        Member result= repository.findByName("spring1").get();
        
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        //왜 Error가 났을까? findAll()이 먼저 실행되고 findByName()할때 앞서 저장한 spring1, spring2가 나온 것이다.
        //그래서 이것을 위해서 Test가 하나 끝나고나면 data를 clear해줘야 한다.
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); //shift F6
        member2.setName("spring2");
        repository.save(member2);

        //Member result = repository.findById(member.getId()).get();
        List<Member> result= repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
