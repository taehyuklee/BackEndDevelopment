package hellojpa.entity;

import javax.persistence.*;
import java.util.Date;

//이제 Entity 객체를 만들어보자 여기서
@Entity(name = "Member")
@Table(name = "Member")
public class Member {

    // PK Mapping한거
    @Id
    private Long id;

    //만약 내가 어플리케이션에서 속성을 name으로 쓰고싶은데 DB의 컬럼 이름이 username이라면? @Column을 사용하면 된다.
    @Column(name = "username", nullable = false)
    private String name;

    private Integer age;

    //Enum type을 쓰고싶다면? @Enumerated를 쓰면된다. 결국 DB에는 STRING으로 저장되므로 STRING을 해줘야 한다.
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    //보통 데이터베이스에서는 날짜, 시간, 날짜시간(timestamp)를 모두 구분해서 사용한다
    @Temporal(TemporalType.TIMESTAMP) 
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifeidDate;

    @Lob //database에서 CLOB, BLOB 사용
    private String description;

    public Member(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Member(){ }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "id: " + id + " name: " + name + " ";
    }

}
