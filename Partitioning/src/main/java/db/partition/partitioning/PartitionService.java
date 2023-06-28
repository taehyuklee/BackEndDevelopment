package db.partition.partitioning;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartitionService {

    private final JdbcTemplate template;

    public void createTraffic(){

        String statesment = "create table \"TRAFFIC\" (id bigint, startTime timeStamp, endTime timeStamp, contents varchar(50))";

        log.info("새로운 Partition Table 생성을 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 생성이 종료되었습니다.");
    }

    public void deleteTraffic(){

        String statesment = "drop table \"TRAFFIC\"";

        log.info("Partition Table 삭제를 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 삭제했습니다.");
    }

}
