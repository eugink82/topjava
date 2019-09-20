package ru.javawebinar.topjava.web.json;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void testWriteOnlyAccess(){
        String json=JsonUtil.writeValue(USER);
        System.out.println(json);
        assertThat(json,not(containsString("password")));
        String userWithPassword= UserTestData.jsonWithPassword(USER,"newPass");
        System.out.println(userWithPassword);
        User user=JsonUtil.readValue(userWithPassword,User.class);
        assertEquals("newPass",user.getPassword());
    }
}
