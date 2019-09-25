package ru.javawebinar.topjava.web;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil {

    private MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code){
        return getMessage(code, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Locale locale){
        return messageSource.getMessage(code,null,locale);
    }
}
