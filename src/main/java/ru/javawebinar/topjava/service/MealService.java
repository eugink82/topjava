package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MealService {
    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId){
        return repository.save(meal,userId);
    }

    public void delete(int id, int userId){
        ValidationUtil.checkNotFoundWithId(repository.delete(id,userId), id);
    }

    public Meal get(int id, int userId){
        return ValidationUtil.checkNotFoundWithId(repository.get(id,userId),id);
    }

    public void update(Meal meal,int userId){
        ValidationUtil.checkNotFoundWithId(repository.save(meal,userId),meal.getId());
    }

    public List<Meal> getAll(int userId){
        return repository.getAll(userId);
    }

    public List<Meal> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId){
        return getBetweenDateTimes(DateTimeUtil.assignStartDateTime(startDate),DateTimeUtil.assignEndDateTime(endDate),userId);
    }

    private List<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId){
        return repository.getBetween(startDateTime,endDateTime,userId);
    }
 }
