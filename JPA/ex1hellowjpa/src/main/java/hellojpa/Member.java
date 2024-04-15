package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

//이제 Entity 객체를 만들어보자 여기서
@Entity
public class Member {

    @Id
    private Long id;
    private String name;

    public Member(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Member(){

    }

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

}
