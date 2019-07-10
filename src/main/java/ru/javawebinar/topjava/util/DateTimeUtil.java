package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter PATTERN_DATETIME_FORMATTER=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final LocalDate MIN_DATE=LocalDate.of(1900,1,1);
    private static final LocalDate MAX_DATE=LocalDate.of(2100,1,1);

    private DateTimeUtil() {
    }

    public static LocalDateTime assignStartDateTime(LocalDate lDate){
        return assignDateTime(lDate,MIN_DATE,LocalTime.MIN);
    }

    public static LocalDateTime assignEndDateTime(LocalDate lDate){
        return assignDateTime(lDate,MAX_DATE,LocalTime.MAX);
    }

    private static LocalDateTime assignDateTime(LocalDate localDate, LocalDate defaultDate, LocalTime defaultTime){
        return LocalDateTime.of(localDate!=null ? localDate : defaultDate, defaultTime);
    }

    public static String toString(LocalDateTime ldt){
        return ldt==null ? "" : ldt.format(PATTERN_DATETIME_FORMATTER);
    }

    public static LocalDate parseToLocalDate(@Nullable String dt){
        return StringUtils.isEmpty(dt) ? null : LocalDate.parse(dt);
    }

    public static LocalTime parseToLocalTime(@Nullable String time){
        return StringUtils.isEmpty(time) ? null : LocalTime.parse(time);
    }

}
