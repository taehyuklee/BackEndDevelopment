package orm.jpa.domain.onedirection;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class OneTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

}
