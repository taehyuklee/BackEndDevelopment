package orm.jpa.domain.bidirection;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orm.jpa.domain.bidirection.BiTeam;

@Entity(name = "BI_DIR_MEM")
@Getter
@Setter
@NoArgsConstructor
public class BiMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="USERNAME", unique = true, length=10, nullable = false) // Only for DDL Function. Not related to Logic
    private String username;

    // 위에서 처럼 외래키처럼 temaId를 쓰는게 아니라 객체 지향에서는 Team 객체를 사용
    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private BiTeam biTeam;

}
