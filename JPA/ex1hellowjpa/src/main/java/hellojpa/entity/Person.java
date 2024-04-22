package hellojpa.entity;

import javax.persistence.*;


@Entity
public class Person {

    @Id //직접 할당
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * GenrationType.AUTO는 DB방언에 맞춰서 자동으로 할당될 경우를 의미한다. Sequence등에 맞춰서 알아서 할당된다.
     * GenrationType.IDENTITY DB가 알아서 올려주는걸 의미한다 MySQL의 경우 AutoIncrement 등
     * GenrationType.SEQUENCE ORACLE에서 많이 사용함
     *
     */


    private String personNm;

}
