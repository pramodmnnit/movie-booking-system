package com.movie.booking.system.util;

import org.joda.time.DateTime;

import java.time.*;
import java.util.Date;

public class DateUtil {

    public static DateTime getDateTimeByDateAndTime(Date date, int time24hr) {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        int hour = time24hr / 100;
        int minute = time24hr % 100;
        LocalTime localTime = LocalTime.of(hour, minute);

        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        Date legacyDate = Date.from(instant);

        return new DateTime(legacyDate);
    }
}
