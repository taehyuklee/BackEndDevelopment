package db.partition.partitioning.service;

import db.partition.partitioning.Interface.Partition;
import db.partition.partitioning.aop.annotation.Retry;
import db.partition.partitioning.utility.PartitionUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(
        value = "common.partition.period-ref"
        ,havingValue = "month"
        ,matchIfMissing = false
)
public class MonthlyPartitionService {

    private final JdbcTemplate template;
    private final PartitionUtility partitionUtility;

    @Retry
    @Scheduled(cron="0 0 0 0 * *")
    @Transactional
    void generatePartition() {
        LocalDate currentDate = LocalDate.now();
        String startDate = partitionUtility.getStartOfMonth(currentDate);
        String endDate = partitionUtility.getEndOfMonth(currentDate);
        String partitionName = partitionUtility.getPartitionMonthName(currentDate);

        //query 만들기
        String statement = "create table public.\"TRAFFIC_"+partitionName+"\" partition of public.\"TRAFFIC\" for values from ('" + startDate + "') to ('"  +endDate + "')";

        log.info("새로운 Partition Table 생성을 시작합니다.");
        template.execute(statement);
        log.info("Partition Table 생성이 종료되었습니다.");
    }

    @Retry
    @Scheduled(cron="0 0 0 0 * *")
    @Transactional
    void dropPartition() {
        LocalDate currentDate = LocalDate.now();
        String partitionName = partitionUtility.getDelPartitionMonthName(currentDate);
        log.info(partitionName);

        //query 만들기
        String statement = "drop table public.\"TRAFFIC_"+partitionName+"\"";

        log.info("새로운 Partition Table 삭제를 시작합니다.");
        template.execute(statement);
        log.info("Partition Table 삭제가 종료되었습니다.");
    }
}
