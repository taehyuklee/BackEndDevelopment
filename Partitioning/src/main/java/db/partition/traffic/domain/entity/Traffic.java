package db.partition.traffic.domain.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Traffic {

    @Id
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String contents;


}
