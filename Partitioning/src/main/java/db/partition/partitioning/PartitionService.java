package db.partition.partitioning;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@RequiredArgsConstructor
public class PartitionService {

    private final JdbcTemplate template;

    public void createTraffic(){

        String createStatesment = "create table Traffic (id Long, startTime LocalDateTime, endTime LocalDateTime, contents varchar)";

        log.info("새로운 Partition Table 생성을 시작합니다.");
        template.execute(createStatesment);
        log.info("Partition Table 생성이 종료되었습니다.");
    }

}
