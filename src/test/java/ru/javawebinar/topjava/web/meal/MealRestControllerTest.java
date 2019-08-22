package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.Month;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

class MealRestControllerTest extends AbstractControllerTest {
    public static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService mealService;

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MEAL_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(START_SEQ), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MEAL_ID))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(mealService.get(MEAL_ID, USER_ID), MEAL1);
    }

    @Test
    void createWithLocations() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.JUNE, 22, 8, 0, 0), "Вкусная жрачка", 900);
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());

        Meal returnedMeal = TestUtil.readFromJson(actions, Meal.class);
        newMeal.setId(returnedMeal.getId());
        assertMatch(returnedMeal, newMeal);
        assertMatch(mealService.getAll(START_SEQ), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void update() throws Exception {
        Meal meal = updatedMeal();
        meal.setDescription("Еще более вечерний ланч");
        meal.setCalories(725);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + MEAL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(MEAL_ID, START_SEQ), meal);


    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(MEALS, USER.getCaloriesPerDay())));
    }

    @Test
    void getBetween() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "between?startDateTime=2015-05-30T06:00:00&endDateTime=2015-05-31T14:00:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(MealsUtil.createMealToExcess(MEAL5, true), MealsUtil.createMealToExcess(MEAL4, true),
                        MealsUtil.createMealToExcess(MEAL2, false), MealsUtil.createMealToExcess(MEAL1, false)));

    }
}