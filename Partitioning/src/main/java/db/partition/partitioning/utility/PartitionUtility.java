package db.partition.partitioning.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    /*Monthly-part*/
    public String getStartOfMonth(LocalDate localDate){
        return localDate.withDayOfMonth(1).toString();
    }

    public String getEndOfMonth(LocalDate localDate){
        return localDate.withDayOfMonth(localDate.lengthOfMonth()).toString();
    }

    public String getPartitionMonthName(LocalDate localDate){
        String partitionNm = localDate.toString().replace("-", "");
        return partitionNm.substring(0, partitionNm.length()-2);
    }

    public String getDelPartitionMonthName(LocalDate localDate){
        log.info("period:{}", monthPeriod);
        String partitionNm = localDate.minusMonths(monthPeriod+1).toString().replace("-", "");
        return partitionNm.substring(0, partitionNm.length()-2);
    }


    /* Daily Part*/
    public String getStartOfDay(LocalDate localDate){
        LocalTime startTime = LocalTime.MIN; // 00:00:00
        LocalDateTime startDateTime = LocalDateTime.of(localDate, startTime);
        return startDateTime.toString();
    }

    public String getEndOfDay(LocalDate localDate){
        LocalTime endTime = LocalTime.of(23, 59, 59); // 23:59:59
        LocalDateTime endDateTime = LocalDateTime.of(localDate, endTime);
        return endDateTime.toString();
    }

    public String getPartitionDailyName(LocalDate localDate){
        String partitionNm = localDate.toString().replace("-", "");
        return partitionNm.substring(0, partitionNm.length());
    }

    public String getDelPartitionDailyName(LocalDate localDate){
        String partitionNm = localDate.minusDays(dayPeriod+1).toString().replace("-", "");
        return partitionNm.substring(0, partitionNm.length());
    }
}
