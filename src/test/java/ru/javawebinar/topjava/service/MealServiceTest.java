package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(MealServiceTest.class);
    @Autowired
    private MealService service;

    @Test
    public void create() {
        long time1 = System.currentTimeMillis();
        Meal newMeal = createdMeal();
        Meal createdMeal = service.create(newMeal, USER_ID);
        newMeal.setId(createdMeal.getId());
        assertMatch(service.getAll(USER_ID), Arrays.asList(newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
        long time2 = System.currentTimeMillis();
        long time3 = time2 - time1;
        LOG.info("Время выполнения теста create - { }", time3);
    }

    @Test
    public void delete() {
        service.delete(MEAL1.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotExists() {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotOwn() {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL1.getId(), USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotExists() {
        Meal meal = service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotOwn() {
        Meal meal = service.get(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void update() {
        Meal meal = updatedMeal();
        service.update(meal, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotExists() {
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEALS);

    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID);
        assertMatch(meals, MEAL3, MEAL2, MEAL1);
    }
}