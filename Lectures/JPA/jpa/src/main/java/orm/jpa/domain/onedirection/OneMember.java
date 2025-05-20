package orm.jpa.domain.onedirection;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "ONE_DIR_MEM")
@Getter
@Setter
@NoArgsConstructor
public class OneMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="USERNAME", unique = true, length=10, nullable = false) // Only for DDL Function. Not related to Logic
    private String username;

//    @Column(name= "TEAM_ID")
//    private Long teamId;
    //위와 같이 직접 Mapping할 경우 충돌 문제가 생긴다.

    // 위에서 처럼 외래키처럼 temaId를 쓰는게 아니라 객체 지향에서는 Team 객체를 사용
    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private OneTeam oneTeam;

}
