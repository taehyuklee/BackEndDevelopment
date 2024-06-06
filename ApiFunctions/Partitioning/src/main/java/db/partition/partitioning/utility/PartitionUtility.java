package db.partition.partitioning.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class PartitionUtility {

    @Value("${common.partition.day-period}")
    private int dayPeriod;

    @Value("${common.partition.month-period}")
    private int monthPeriod;


    /* Daily Part*/
    public String getStartOfDay(LocalDate localDate){
        LocalTime startTime = LocalTime.MIN; // 00:00:00
        LocalDateTime startDateTime = LocalDateTime.of(localDate.plusDays(dayPeriod+1), startTime);
        Long startFinalTime = Timestamp.valueOf(startDateTime).getTime(); //timeStamp로 바꿔주기
        return startFinalTime.toString();
    }

    public String getEndOfDay(LocalDate localDate){
        LocalTime endTime = LocalTime.of(23, 59, 59,999999999); // 23:59:59
        LocalDateTime endDateTime = LocalDateTime.of(localDate.plusDays(dayPeriod+1), endTime);
        Long endFinalTime = Timestamp.valueOf(endDateTime).getTime(); //timeStamp로 바꿔주기
        return endFinalTime.toString();
    }

    public String getPartitionDailyName(LocalDate localDate){
        String partitionNm = localDate.plusDays(dayPeriod+1).toString().replace("-", "");
        return partitionNm.substring(0, partitionNm.length());
    }

    public String getDelPartitionDailyName(LocalDate localDate){
        String partitionNm = localDate.minusDays(dayPeriod+1).toString().replace("-", "");
        return partitionNm.substring(0, partitionNm.length());
    }


    /*Monthly-part*/
    public String getStartOfMonth(LocalDate localDate){
        LocalDateTime medTime = localDate.plusMonths(monthPeriod+1).withDayOfMonth(1).atStartOfDay(); //LocalDate to LocalDateTime
        Long startFinalTime = Timestamp.valueOf(medTime).getTime(); //timeStamp로 바꿔주기
        return startFinalTime.toString();
    }

    public String getEndOfMonth(LocalDate localDate){
        LocalDateTime medTime = localDate.plusMonths(monthPeriod+2).withDayOfMonth(1).atStartOfDay(); //LocalDate to LocalDateTime
        Long endFinalTime = Timestamp.valueOf(medTime).getTime(); //timeStamp로 바꿔주기
        return endFinalTime.toString();
    }

    public String getPartitionMonthName(LocalDate localDate){
        String partitionNm = localDate.plusMonths(monthPeriod+1).toString().replace("-", "");
        return partitionNm.substring(0, partitionNm.length()-2);
    }

    public String getDelPartitionMonthName(LocalDate localDate){
        log.info("period:{}", monthPeriod); //202308 -3
        String partitionNm = localDate.minusMonths(monthPeriod+1).toString().replace("-", "");
        return partitionNm.substring(0, partitionNm.length()-2);
    }

}
