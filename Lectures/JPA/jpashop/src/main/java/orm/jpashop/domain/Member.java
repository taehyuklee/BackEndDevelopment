package orm.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "MEMBER_ID")
    private Long id;

    private String name;

    private String city;

    private String street;

    private String zipcode;

    // 만약 양방향 관계를 가져 가겠다 하면 (연관관계의 주인)
//    @OneToMany(mappedBy = "member")
//    private List<Order> orders = new ArrayList<>();


}
