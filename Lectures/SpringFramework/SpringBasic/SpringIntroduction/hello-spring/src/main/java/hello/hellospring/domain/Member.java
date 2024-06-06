package hello.hellospring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

    //query에 ID안내고 DB에 값을 넣으면 알아서 ID를 생성해주는 것을 Identity전략이라 한다.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //오 Long을 쓰는구나? 8byte
    private String name;

    //Getter Setter
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
