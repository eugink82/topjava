package ru.javawebinar.topjava.web.meal;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate>{
        @Override
        public LocalDate parse(String formatted, Locale locale) throws ParseException {
            if(formatted.length()==0){
                return null;
            }
            return DateTimeUtil.parseLocalDate(formatted);
        }

        @Override
        public String print(LocalDate date, Locale locale) {
            if(date==null){
                return "";
            }
            return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
    public static class LocalTimeFormatter implements Formatter<LocalTime>{
        @Override
        public LocalTime parse(String formatted, Locale locale) throws ParseException {
            if(formatted.length()==0){
                return null;
            }
            return DateTimeUtil.parseLocalTime(formatted);
        }

        @Override
        public String print(LocalTime date, Locale locale) {
            if(date==null){
                return "";
            }
            return date.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }

}
