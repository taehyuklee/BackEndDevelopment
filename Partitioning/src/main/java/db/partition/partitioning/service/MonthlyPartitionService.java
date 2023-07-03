package db.partition.partitioning.service;

import db.partition.partitioning.Interface.Partition;
import db.partition.partitioning.aop.annotation.Retry;
import db.partition.partitioning.utility.PartitionUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class MonthlyPartitionService  implements Partition {

    private final JdbcTemplate template;
    private final PartitionUtility partitionUtility;

    @Retry
    @Transactional
    @Override
    public void generatePartition() {
        LocalDate currentDate = LocalDate.now();
        String startDate = partitionUtility.getStartOfMonth(currentDate);
        String endDate = partitionUtility.getEndOfMonth(currentDate);
        String partionName = partitionUtility.getPartitionMonthName(currentDate);

        String statesment = "create table if not exists public.\"TRAFFIC_"+partionName+"\" partition of public.\"TRAFFIC\" for values from ('" + startDate + "') to ('"  +endDate + "')";

        log.info("새로운 Monthly Partition Table 생성을 시작합니다.");
        template.execute(statesment);
        log.info("Monthly Partition Table 생성이 종료되었습니다.");
    }

    @Retry
    @Transactional
    @Override
    public void dropPartition() {
        LocalDate currentDate = LocalDate.now();
        String statesment = "drop table if exists public.\"TRAFFIC_202307\"";

        log.info("새로운 Monthly Partition Table 삭제를 시작합니다.");
        template.execute(statesment);
        log.info("Monthly Partition Table 삭제가 종료되었습니다.");
    }
}
