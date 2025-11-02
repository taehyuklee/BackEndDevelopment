package com.coin.market.engine.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatterUtil {

    public String dateToString(LocalDateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss");
        return dateTime.format(dateTimeFormatter);
    }

    public LocalDateTime stringToDate(String strDateTime) {

        //System.out.println(strDateTime);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss");

        return LocalDateTime.parse(strDateTime, dateTimeFormatter);
    }
}
