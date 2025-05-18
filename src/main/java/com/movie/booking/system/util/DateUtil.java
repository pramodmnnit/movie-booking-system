package com.movie.booking.system.util;

import org.joda.time.DateTime;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static DateTime getDateTimeByDateAndTime(Date date, Time time) {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalTime localTime = time.toLocalTime();
        LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);
        return new DateTime(dateTime);
    }
}
