package db.partition.traffic.domain.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Traffic {

    @Id
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String contents;


}
