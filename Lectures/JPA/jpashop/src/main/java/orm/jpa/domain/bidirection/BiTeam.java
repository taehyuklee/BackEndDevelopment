package orm.jpa.domain.bidirection;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class BiTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name; 


    // team에서 Member로 가는건 1:N이다. 그리고 mappedBy에는 N에서의 뭐랑 연결되어 있다를 알려주는 인자임. (반대편 side의 attribute name)
    @OneToMany(mappedBy = "biTeam")
    private List<BiMember> members = new ArrayList<>();
}
