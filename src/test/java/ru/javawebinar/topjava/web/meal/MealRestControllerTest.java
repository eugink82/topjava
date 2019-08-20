package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    void getAll() throws Exception{
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(MEALS, USER.getCaloriesPerDay())));

    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL+MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(USER_ID),MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void getMeal() throws Exception {
        mockMvc.perform(get(REST_URL+MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result->assertMatch(TestUtil.readFromJsonMvcResult(result, Meal.class),MEAL1));
    }

    @Test
    void createMeal() throws Exception{
        Meal expected=new Meal(null, of(2015, Month.JUNE, 15, 18, 0), "Созданный ужин", 300);
        ResultActions actions=mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned=TestUtil.readFromJson(actions,Meal.class);
        expected.setId(returned.getId());
        assertMatch(returned,expected);
        assertMatch(mealService.getAll(USER_ID),expected,MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

    }

    @Test
    void update() throws Exception {
        Meal meal=getUpdated();
        meal.setDescription("Более обновленный завтрак");
        meal.setCalories(220);
        mockMvc.perform(put(REST_URL+MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(MEAL1_ID,USER_ID),meal);
    }

    @Test
    void getBetween() throws Exception {
        mockMvc.perform(get(REST_URL+"between?startDate=2015-05-30T12:00:00&endDate=2015-05-31T21:00:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(MealsUtil.createWithExcess(MEAL6,true),MealsUtil.createWithExcess(MEAL5,true),
                        MealsUtil.createWithExcess(MEAL3,false),MealsUtil.createWithExcess(MEAL2,false)));
    }
}