package hellojpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IdentityMember {

    @Id //직접 할당 (이 @Id만 쓰면 내가 직접 할당해줘야 한다)
    //Identity는 DB에 Entity가 들어가봐야 pk값이 할당된다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberNm;

    public IdentityMember() {  }

    public Long getId() { return id; }

    public String getPersonNm() {
        return memberNm;
    }

    public void setPersonNm(String personNm) {
        this.memberNm = personNm;
    }

}
