package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
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
import java.util.concurrent.TimeUnit;

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
    private static StringBuilder timeWorkTests=new StringBuilder();

    @Rule
    public ExpectedException thrown=ExpectedException.none();

    @Rule
    public Stopwatch stopwatch=new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String timeTest="Тест: "+description.getMethodName()+", время работы: "+ TimeUnit.NANOSECONDS.toMillis(nanos)+"ms";
            timeWorkTests.append(timeTest).append(System.lineSeparator());
        }
    };

    @Autowired
    private MealService service;

    @AfterClass
    public static void fullResults(){
        LOG.info(timeWorkTests+"");
    }

    @Test
    public void create() {
        Meal newMeal = createdMeal();
        Meal createdMeal = service.create(newMeal, USER_ID);
        newMeal.setId(createdMeal.getId());
        assertMatch(service.getAll(USER_ID), Arrays.asList(newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void delete() {
        service.delete(MEAL1.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void deleteNotExists() {
        thrown.expect(NotFoundException.class);
        service.delete(1, USER_ID);
    }

    @Test
    public void deleteNotOwn() {
        thrown.expect(NotFoundException.class);
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL1.getId(), USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test
    public void getNotExists() {
        thrown.expect(NotFoundException.class);
        Meal meal = service.get(1, USER_ID);
    }

    @Test
    public void getNotOwn() {
        thrown.expect(NotFoundException.class);
        Meal meal = service.get(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void update() {
        Meal meal = updatedMeal();
        service.update(meal, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), meal);
    }

    @Test
    public void updateNotExists() {
        thrown.expect(NotFoundException.class);
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