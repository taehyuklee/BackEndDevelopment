package db.partition.partitioning.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartitionService {

    private final JdbcTemplate template;

    public void createTraffic(){

        String statesment = "create table public.\"TRAFFIC\" (id bigint, startTime timeStamp, endTime timeStamp, contents varchar(50)) partition by range (endtime)";

        log.info("새로운 Partition Table 생성을 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 생성이 종료되었습니다.");
    }

    public void deleteTraffic(){

        String statesment = "drop table public.\"TRAFFIC\"";

        log.info("Partition Table 삭제를 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 삭제했습니다.");
    }

    public void partitionTraffic(){

        LocalDate currentDate = LocalDate.now();
        String currentStringDate = currentDate.atStartOfDay().toString();
        log.info("currentDate:{}", currentStringDate);

        String startDate = "2023-10-01";
        String endDate = "2023-10-31";

        String statesment = "create table public.\"TRAFFIC_202310\" partition of public.\"TRAFFIC\" for values from ('" + startDate + "') to ('"  +endDate + "')";

        log.info("새로운 Partition Table 생성을 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 생성이 종료되었습니다.");
    }

    public void deletePartition(){

        String statesment = "drop table if exists public.\"TRAFFIC_202310\" ";

        log.info("새로운 Partition Table 삭제를 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 삭제가 종료되었습니다.");
    }
}
