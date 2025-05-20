package orm.jpa.service;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orm.jpa.domain.bidirection.BiMember;
import orm.jpa.domain.bidirection.BiTeam;
import orm.jpa.domain.keymapping.MemberForKey;
import orm.jpa.domain.onedirection.OneMember;
import orm.jpa.domain.onedirection.OneTeam;

import java.util.List;

@Service
@RequiredArgsConstructor
//@NoArgsConstructor //No Args랑 RquiredArgs 충돌함 <- 이거 확실히 알아야할듯
public class MemberService {
    
    // Spring Data JPA에서는 생성자 주입해 놓은 상태로 em을써야함 굳이 쓸거면
    private final EntityManager em;

    // JPA 영속성 관련한 내용
    @Transactional
    public String insertMember(MemberForKey memberForKey){
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hello");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();

//        try {

//        memberForKey.setId(100L);
        memberForKey.setUsername("HelloJPA");

        //영속
        em.persist(memberForKey);

        System.out.println(memberForKey.getId());

//        } catch (Exception e) {
//            tx.rollback();
//        }finally {
//            em.close();
//        }

//        emf.close();

        return "Success";

    }

    // 일대 다 1:n 단방향
    @Transactional
    public String oneDirTeamAndMember(){

        /*
        *   이렇게 하면 외래키 방식으로 객체에 도입하게 된 것임.
        *   하지만, 이렇게 되면 객체지향적이지가 않다.
        *   team Id를 뽑을때
         * */
        OneTeam oneTeam = new OneTeam();
        oneTeam.setName("TeamA");
        em.persist(oneTeam);

        OneMember oneMember = new OneMember();

        oneMember.setUsername("member1");
//        memberRelation.setTeamId(team.getTeamId());
//        em.persist(memberRelation);


        // 객체 지향 형으로는 @연관관계 Mapping하면 다음과 같이 된다. (단방향 mapping)
        oneMember.setOneTeam(oneTeam);

        em.persist(oneMember);

        OneTeam oneTeam2 = oneMember.getOneTeam();
        System.out.println(oneTeam2);

        return "Success";

    }

    // 일대 다 1:n 양방향
    @Transactional
    public String biDirTeamAndMember(){

        // team
        BiTeam biTeam = new BiTeam();
        biTeam.setName("TeamA");
        em.persist(biTeam);

        BiMember biMember = new BiMember();
        biMember.setUsername("member1");
        biMember.setBiTeam(biTeam);
        em.persist(biMember);

        em.flush();
        em.clear();

        // 영속성에서 찾는다.
        BiMember targetMem = em.find(BiMember.class, biMember.getId());
        List<BiMember> members = targetMem.getBiTeam().getMembers();

        for (BiMember m: members) {
            System.out.println("m = " + m.getId());
        }


        return "Success";

    }

}
