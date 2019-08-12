package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AbstractMealController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MealService mealService;

    public void delete(int id){
        int userId= SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        mealService.delete(id,userId);
    }

    public Meal get(int id){
        int userId=SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        return mealService.get(id,userId);
    }

    public Meal create(Meal meal){
        int userId= SecurityUtil.authUserId();
        ValidationUtil.checkNew(meal);
        log.info("create {} for user {}", meal, userId);
        return mealService.create(meal,userId);
    }

    public void update(Meal meal, int id){
        int userId=SecurityUtil.authUserId();
        ValidationUtil.assureIdConsistent(meal,id);
        log.info("update {} for user {}", meal, userId);
        mealService.update(meal,userId);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return MealsUtil.getWithExcess(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }



    public List<MealTo> getBetween(LocalDate startDt, LocalTime startTime, LocalDate endDt, LocalTime endTime){
        int userId=SecurityUtil.authUserId();
        List<Meal> meals= mealService.getBetweenDates(startDt,endDt,userId);
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDt, endDt, startTime, endTime, userId);
        return MealsUtil.getFilteredWithExcess(meals,startTime,endTime,SecurityUtil.authUserCaloriesPerDay());
    }
}
