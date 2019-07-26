package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

import ru.javawebinar.topjava.model.User;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void userWithMeals() {
        User user = service.getUserWithMeals(USER_ID);
        assertMatch(user, USER);
        assertMatch(user.getMeals(), MEALS);
    }
}
