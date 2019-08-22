package ru.javawebinar.topjava.web.converter;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate>{
        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return DateTimeUtil.parseToLocalDate(text);
        }

        @Override
        public String print(LocalDate localDate, Locale locale) {
            return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
    public static class LocalTimeFormatter implements Formatter<LocalTime>{
        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
            return DateTimeUtil.parseToLocalTime(text);
        }

        @Override
        public String print(LocalTime localTime, Locale locale) {
            return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }
}
