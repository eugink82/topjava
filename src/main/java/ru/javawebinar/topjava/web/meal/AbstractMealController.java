package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public class AbstractMealController {

    @Autowired
    private MealService mealService;

    public void delete(int id){
        int userId = SecurityUtil.authUserId();
        mealService.delete(id, userId);
    }

    public List<MealTo> filter(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        List<Meal> mealsDateFiltered = mealService.getBetweenDates(startDate, endDate, userId);
        return MealsUtil.getFilteredWithExcess(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

    public Meal get(int id){
        int userId = SecurityUtil.authUserId();
        return mealService.get(id, userId);
    }

    public Meal create(Meal meal){
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        return mealService.create(meal, userId);
    }

    public void update(Meal meal, int id){
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        mealService.update(meal, userId);
    }


}
