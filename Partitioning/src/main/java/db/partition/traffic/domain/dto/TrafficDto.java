package db.partition.traffic.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrafficDto {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String contents;


}
