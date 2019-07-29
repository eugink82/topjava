package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void testUserWithMeals(){
        User user=service.userWithMeals(USER_ID);
        assertMatch(user,USER);
        MealTestData.assertMatch(user.getMeals(),MEALS);
    }

    @Test
    public void testUserWithoutMeal(){
        thrown.expect(NotFoundException.class);
        service.userWithMeals(1);
    }
}
