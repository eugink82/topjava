package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonObjectMapper extends ObjectMapper {
    private static final ObjectMapper MAPPER=new JacksonObjectMapper();
    private JacksonObjectMapper(){
        registerModule(new Hibernate5Module());
        registerModule(new JavaTimeModule());
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD,JsonAutoDetect.Visibility.ANY);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
