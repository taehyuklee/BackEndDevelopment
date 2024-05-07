package hellojpa.entity;

import javax.persistence.*;

//기본 SEQUENCE
@Entity
public class Person {


    @Id //직접 할당 (이 @Id만 쓰면 내가 직접 할당해줘야 한다)
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //DB가 알아서 할당해준다.
    private Long id;
    /**
     * GenrationType.AUTO는 DB방언에 맞춰서 자동으로 할당될 경우를 의미한다. Sequence등에 맞춰서 알아서 할당된다.
     * GenrationType.IDENTITY DB가 알아서 올려주는걸 의미한다 MySQL의 경우 AutoIncrement 등
     * GenrationType.SEQUENCE ORACLE에서 많이 사용함
     *
     */

    private String personNm;

    public Person() {  }

    public String getPersonNm() {
        return personNm;
    }

    public void setPersonNm(String personNm) {
        this.personNm = personNm;
    }
}
