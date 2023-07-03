package db.partition.partitioning.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PartitionUtility {

    public static String getStartOfMonth(LocalDate localDate){
        return localDate.withDayOfMonth(1).toString();
    }

    public static String getEndOfMonth(LocalDate localDate){
        return localDate.withDayOfMonth(localDate.lengthOfMonth()).toString();
    }

    public static String getPartitionName(LocalDate localDate){
        return localDate.toString().replace("-", "");
    }

}
