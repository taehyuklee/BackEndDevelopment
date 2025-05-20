package orm.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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


}
