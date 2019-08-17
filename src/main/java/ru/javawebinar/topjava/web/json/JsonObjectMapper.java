package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

public class JsonObjectMapper extends ObjectMapper {
    JsonObjectMapper(){
        registerModule(new Hibernate5Module());
        setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD,JsonAutoDetect.Visibility.ANY);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
