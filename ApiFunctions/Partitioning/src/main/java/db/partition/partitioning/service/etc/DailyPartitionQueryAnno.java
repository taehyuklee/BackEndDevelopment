package db.partition.partitioning.service.etc;

import db.partition.partitioning.Interface.Partition;
import db.partition.partitioning.aop.annotation.Retry;
import db.partition.partitioning.repository.TrafficPartitionRepository;
import db.partition.partitioning.utility.PartitionUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

//Query Annotation으로 시도하였으나 되질 않음
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(
        value = "common.partition.period-ref"
        ,havingValue = "day"
        ,matchIfMissing = false
)
public class DailyPartitionQueryAnno implements Partition {

    private final JdbcTemplate template;
    private final PartitionUtility partitionUtility;

    private final DataSource dataSource;

    private final TrafficPartitionRepository trafficRepo;

    @Retry
//    @Scheduled(cron="0/5 * * * * *")
    @Override
    @Transactional
    public void generatePartition() throws SQLException {
        LocalDate currentDate = LocalDate.now();
        String startDate = partitionUtility.getStartOfDay(currentDate); //07-03:00:00:00
        String endDate = partitionUtility.getEndOfDay(currentDate); //07-03-23:59:59
        String partionName = partitionUtility.getPartitionDailyName(currentDate); //20230703

        log.info("새로운 Partition Table 생성을 시작합니다.");

        trafficRepo.createTrafficTable();
        log.info("Partition Table 생성이 종료되었습니다.");
    }

    @Retry
//    @Scheduled(cron="0 0/1 0 * * *")
    @Override
    @Transactional
    public void dropPartition() {
        String partionName = partitionUtility.getDelPartitionDailyName(LocalDate.now());

        //query 만들기
        String statesment = "drop table public.\"TRAFFIC_"+partionName+"\"";

        log.info("새로운 Partition Table 삭제를 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 삭제가 종료되었습니다.");
    }
}
