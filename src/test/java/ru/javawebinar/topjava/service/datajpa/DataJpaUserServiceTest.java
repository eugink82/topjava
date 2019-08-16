package ru.javawebinar.topjava.service.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertThrows;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    void testUserWithMeals(){
        User admin=service.userWithMeals(ADMIN_ID);
        assertMatch(admin,ADMIN);
        MealTestData.assertMatch(admin.getMeals(),ADMIN_MEAL2,ADMIN_MEAL1);
    }

    @Test
    void testUserWithoutMeal(){
        assertThrows(NotFoundException.class,()->
        service.userWithMeals(1));
    }
}
