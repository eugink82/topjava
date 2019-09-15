package ru.javawebinar.topjava.service;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;



public abstract class AbstractMealServiceTest extends AbstractServiceTest{

    @Autowired
    protected MealService service;

    @Test
    void create() {
        Meal newMeal = createdMeal();
        Meal createdMeal = service.create(newMeal, USER_ID);
        newMeal.setId(createdMeal.getId());
        assertMatch(service.getAll(USER_ID), Arrays.asList(newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test
    void delete() {
        service.delete(MEAL1.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void deleteNotExists() {
        assertThrows(NotFoundException.class,()->
        service.delete(1, USER_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class,()->
        service.delete(MEAL_ID, ADMIN_ID));
    }

    @Test
    void get() {
        Meal meal = service.get(MEAL1.getId(), USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test
    void getNotExists() {
        assertThrows(NotFoundException.class,()->
        service.get(1, USER_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class,()->
        service.get(MEAL_ID, ADMIN_ID));
    }

    @Test
    void update() {
        Meal meal = updatedMeal();
        service.update(meal, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), meal);
    }

    @Test
    void updateNotExists() {
        NotFoundException e=assertThrows(NotFoundException.class,()->
        service.update(MEAL1, ADMIN_ID));
        assertEquals(e.getMessage(), "Не найдена сущность с id="  + MEAL_ID);
    }

    @Test
    void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEALS);

    }

    @Test
    void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID);
        assertMatch(meals, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void createWithException() throws Exception {
        validationRootCause(() -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300), USER_ID), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new Meal(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 9), USER_ID), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 10001), USER_ID), ConstraintViolationException.class);
    }
}