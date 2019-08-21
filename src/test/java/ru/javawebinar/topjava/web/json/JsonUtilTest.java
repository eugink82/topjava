package ru.javawebinar.topjava.web.json;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

public class JsonUtilTest {

    @Test
    void readWriteValue(){
        String json=JsonUtil.writeValue(MEAL1);
        System.out.println(json);
        Meal meal=JsonUtil.readValue(json,Meal.class);
        assertMatch(meal,MEAL1);

    }

    @Test
    void readWriteValues(){
       String json=JsonUtil.writeValue(MEALS);
        System.out.println(json);
        List<Meal> meals=JsonUtil.readValues(json,Meal.class);
        assertMatch(meals,MEALS);

    }
}
