package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest{
    @Test
    public void testMealWithUser(){
        Meal meal=service.mealWithUser(MEAL_ID,USER_ID);
        assertMatch(meal,MEAL1);
        assertMatch(meal.getUser(),USER);
    }

    @Test
    public void testMealWithNotUser(){
        thrown.expect(NotFoundException.class);
        service.mealWithUser(MEAL_ID,1);
    }
}
