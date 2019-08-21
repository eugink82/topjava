package ru.javawebinar.topjava.service.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    void MealWithUser() {
        Meal meal = service.mealWithUser(MEAL_ID, USER_ID);
        assertMatch(meal, MEAL1);
        assertMatch(meal.getUser(), USER);
    }

    @Test
    void MealWithNotUser() {
        assertThrows(NotFoundException.class, () ->
                service.mealWithUser(MEAL_ID, 1));
    }
}
