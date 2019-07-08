package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEAL_6;
import static ru.javawebinar.topjava.MealTestData.MEAL_5;
import static ru.javawebinar.topjava.MealTestData.MEAL_4;
import static ru.javawebinar.topjava.MealTestData.MEAL_3;
import static ru.javawebinar.topjava.MealTestData.MEAL_2;
import static ru.javawebinar.topjava.MealTestData.MEAL_1;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2018, Month.JULY, 5, 13, 0), "Хавка", 530);
        Meal mealCreated = mealService.create(newMeal, USER_ID);
        newMeal.setId(mealCreated.getId());
        List<Meal> meals = Arrays.asList(newMeal, MEAL_7, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
        assertMatch(mealService.getAll(USER_ID), meals);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_ID, USER_ID);
        assertMatch(mealService.getAll(USER_ID), Arrays.asList(MEAL_7, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2));
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlienMeal() {
        mealService.delete(MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        mealService.delete(1, USER_ID);
    }

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL_ID, USER_ID);
        assertMatch(meal, MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getAlienMeal() {
        Meal meal = mealService.get(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void update() {
        Meal meal = new Meal(MEAL_1);
        meal.setDescription("Поешка");
        meal.setCalories(333);
        mealService.update(meal, USER_ID);
        assertMatch(mealService.get(MEAL_ID, USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlienMeal() {
        Meal meal = new Meal(MEAL_1);
        meal.setDescription("Поешка");
        meal.setCalories(333);
        mealService.update(meal, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = mealService.getBetweenDates(LocalDate.of(2015, 5, 31), LocalDate.of(2015, 6, 4), USER_ID);
        assertMatch(meals, MEAL_7, MEAL_6, MEAL_5, MEAL_4);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = mealService.getBetweenDateTimes(LocalDateTime.of(2015, 5, 30, 7, 0), LocalDateTime.of(2015, 5, 30, 14, 0), USER_ID);
        assertMatch(meals, MEAL_2, MEAL_1);
    }

    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals, Arrays.asList(MEAL_7, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1));
    }
}