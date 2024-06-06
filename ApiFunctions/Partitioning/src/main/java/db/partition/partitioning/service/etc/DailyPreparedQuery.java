package db.partition.partitioning.service;

import db.partition.partitioning.Interface.Partition;
import db.partition.partitioning.aop.annotation.Retry;
import db.partition.partitioning.repository.TrafficPartitionRepository;
import db.partition.partitioning.utility.PartitionUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(
        value = "common.partition.period-ref"
        ,havingValue = "day"
        ,matchIfMissing = false
)
public class DailyPreparedQuery implements Partition {

    private final JdbcTemplate template;
    private final PartitionUtility partitionUtility;

    private final DataSource dataSource;

    private final TrafficPartitionRepository trafficRepo;

    private final EntityManager entityManager;

    @Retry
    @Scheduled(cron="0 0 0 * * *")
    @Override
    @Transactional
    public void generatePartition() throws SQLException {
        LocalDate currentDate = LocalDate.now();
        String startDate = partitionUtility.getStartOfDay(currentDate); //2023-07-03:00:00:00
        String endDate = partitionUtility.getEndOfDay(currentDate); //2023-07-03-23:59:59
        String partitionName = partitionUtility.getPartitionDailyName(currentDate); //20230703

        //query 만들기
        //String statesment = "create table public.\"TRAFFIC_"+partionName+"\" partition of public.\"TRAFFIC\" for values from ('" + startDate + "') to ('"  +endDate + "')";


        String realNm = "TRAFFIC_" + partitionName;
        //String query = String.format("create table public.\"TRAFFIC_%s\" partition of public.\"TRAFFIC\" for values from (?) to (?)", partionName);
        String query = "create table public.\"TRAFFIC_%s\" partition of public.\"TRAFFIC\" for values from (?) to (?)";
        Connection  con  = dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, startDate);
        pstmt.setString(2, endDate);


        log.info("새로운 Partition Table 생성을 시작합니다.");
        pstmt.execute();
//        template.execute(query);
        log.info("Partition Table 생성이 종료되었습니다.");
    }

    @Retry
    @Scheduled(cron="0 0 0 * * *")
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

    public void createTrafficPartition(String partitionName, String startDate, String endDate) {

    }
}
