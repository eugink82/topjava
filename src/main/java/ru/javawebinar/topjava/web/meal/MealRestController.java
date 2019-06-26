package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger LOG= LoggerFactory.getLogger(MealRestController.class);
    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal){
        int userId= SecurityUtil.authUserId();
        ValidationUtil.checkNew(meal);
        LOG.debug("create meal{} for use{}",meal, userId);
        return service.create(meal,userId);
    }

    public void delete(int id){
        int userId=SecurityUtil.authUserId();
        LOG.debug("delete meal{} for userId{}",id,userId);
        service.delete(id,userId);
    }

    public Meal get(int id){
        int userId=SecurityUtil.authUserId();
        LOG.debug("get meal{} for user{}",id,userId);
        return service.get(id,userId);
    }

    public void update(Meal meal, int id){
        int userId=SecurityUtil.authUserId();
        ValidationUtil.assureIdConsistent(meal,id);
        LOG.debug("update meal{} for user{}",meal,userId);
        service.update(meal,userId);
    }

    public List<MealTo> getAll(){
        int userId=SecurityUtil.authUserId();
        LOG.debug("getAll for user{}",userId);
        return MealsUtil.getWithExcess(service.getAll(userId),SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getBetween(LocalDate startDt, LocalTime startTime, LocalDate endDt, LocalTime endTime){
        int userId=SecurityUtil.authUserId();
        LOG.info("getBetween dates({} - {}) time({} - {}) for user {}", startDt, endDt, startTime, endTime, userId);
        List<Meal> meals= service.getBetweenDates(startDt,endDt,userId);
        return MealsUtil.getFilteredWithExcess(meals,startTime,endTime,SecurityUtil.authUserCaloriesPerDay());
    }
}
