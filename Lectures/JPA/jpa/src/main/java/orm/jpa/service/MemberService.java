package orm.jpa.service;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orm.jpa.domain.MemberForKey;

@Service
@RequiredArgsConstructor
//@NoArgsConstructor //No Args랑 RquiredArgs 충돌함 <- 이거 확실히 알아야할듯
public class MemberService {
    
    // Spring Data JPA에서는 생성자 주입해 놓은 상태로 em을써야함 굳이 쓸거면
    private final EntityManager em;

    @Transactional
    public String insertMember(MemberForKey memberForKey){

//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hello");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();

//        try {

//        memberForKey.setId(100L);
        memberForKey.setName("HelloJPA");

        //영속
        em.persist(memberForKey);

//        } catch (Exception e) {
//            tx.rollback();
//        }finally {
//            em.close();
//        }

//        emf.close();

        return "Success";

    }

}
