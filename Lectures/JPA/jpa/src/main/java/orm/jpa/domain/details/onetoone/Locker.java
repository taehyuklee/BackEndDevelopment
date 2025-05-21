package orm.jpa.domain.details.onetoone;

import jakarta.persistence.*;

public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker") // 읽기 전용
    private Member member;

}
