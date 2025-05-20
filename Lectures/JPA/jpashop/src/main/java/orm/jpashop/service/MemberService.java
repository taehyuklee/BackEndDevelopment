package orm.jpashop.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
//@NoArgsConstructor //No Args랑 RquiredArgs 충돌함 <- 이거 확실히 알아야할듯
public class MemberService {
    
    // Spring Data JPA에서는 생성자 주입해 놓은 상태로 em을써야함 굳이 쓸거면
    private final EntityManager em;

    // JPA 영속성 관련한 내용
    @Transactional
    public String insertMember(){

        return "Success";

    }

    // 일대 다 1:n 단방향
    @Transactional
    public String oneDirTeamAndMember(){

        return "Success";

    }

}
