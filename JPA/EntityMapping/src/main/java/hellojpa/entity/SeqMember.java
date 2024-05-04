package hellojpa.entity;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50
)
public class SeqMember {

    @Id //직접 할당 (이 @Id만 쓰면 내가 직접 할당해줘야 한다)
    //Sequence는 DB에 들어가기전에 JDBC가 미리 PK값을 Return해와서 알려준다.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR") //DB가 알아서 할당해준다.
    private Long id;

    private String memberNm;

    public SeqMember() {  }

    public Long getId() { return id; }

    public String getPersonNm() {
        return memberNm;
    }

    public void setPersonNm(String personNm) {
        this.memberNm = personNm;
    }
}
