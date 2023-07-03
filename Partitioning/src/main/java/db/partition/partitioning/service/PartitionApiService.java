package db.partition.partitioning.service;

import db.partition.partitioning.Interface.Partition;
import db.partition.partitioning.utility.PartitionUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartitionApiService{

    private final JdbcTemplate template;

    private final PartitionUtility partitionUtility;

    /*Create Partitioned-table*/
    @Transactional
    public void createTraffic(){

        String statesment = "create table public.\"TRAFFIC\" (\"ID\" serial not NULL , \"START_TIME\" timeStamp, \"END_TIME\" timeStamp, \"CONTENTS\" varchar(50), PRIMARY KEY (\"ID\", \"END_TIME\")) partition by range (\"END_TIME\")";

        log.info("새로운 Partition Table 생성을 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 생성이 종료되었습니다.");
    }

    @Transactional
    public void deleteTraffic(){

        String statesment = "drop table public.\"TRAFFIC\"";

        log.info("Partition Table 삭제를 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 삭제했습니다.");
    }

    //Api Test - Monthly
    @Transactional
    public void partitionMonthlyTraffic(){

        LocalDate currentDate = LocalDate.now();
        String startDate = partitionUtility.getStartOfMonth(currentDate);
        String endDate = partitionUtility.getEndOfMonth(currentDate);
        String partionName = partitionUtility.getPartitionMonthName(currentDate);

        String statesment = "create table public.\"TRAFFIC_"+partionName+"\" partition of public.\"TRAFFIC\" for values from ('" + startDate + "') to ('"  +endDate + "')";

        log.info("새로운 Partition Table 생성을 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 생성이 종료되었습니다.");
    }

    @Transactional
    public void deleteMonthlyPartition(){
        LocalDate currentDate = LocalDate.now();
        String partionName = partitionUtility.getDelPartitionMonthName(currentDate);
        log.info(partionName);
        String statesment = "drop table public.\"TRAFFIC_"+partionName+"\""; // if exists (이 부분 빼는게 에러 터뜨림 훨씬 낫다)

        log.info("새로운 Partition Table 삭제를 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 삭제가 종료되었습니다.");
    }


    //Api Test - Daily
    @Transactional
    public void partitionDailyTraffic(){

        LocalDate currentDate = LocalDate.now();
        String startDate = partitionUtility.getStartOfDay(currentDate);
        String endDate = partitionUtility.getEndOfDay(currentDate);
        String partionName = partitionUtility.getPartitionDailyName(currentDate);

        String statesment = "create table public.\"TRAFFIC_"+partionName+"\" partition of public.\"TRAFFIC\" for values from ('" + startDate + "') to ('"  +endDate + "')";

        log.info("새로운 Partition Table 생성을 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 생성이 종료되었습니다.");
    }

    @Transactional
    public void deleteDailyPartition(){
        String partionName = partitionUtility.getDelPartitionDailyName(LocalDate.now());
        log.info(partionName);
        String statesment = "drop table public.\"TRAFFIC_"+partionName+"\""; // if exists (이 부분 빼는게 에러 터뜨림 훨씬 낫다)

        log.info("새로운 Partition Table 삭제를 시작합니다.");
        template.execute(statesment);
        log.info("Partition Table 삭제가 종료되었습니다.");
    }
}
