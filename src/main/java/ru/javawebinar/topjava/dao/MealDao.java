package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class MealDao {
    private final List<Meal> mealsList;

    public MealDao(){
        mealsList=MealsUtil.meals;
    }

    public void addMeal(Meal meal){
        mealsList.add(meal);
    }

    public void deleteMeal(int id){
        mealsList.removeIf(meal -> meal.getId() == id);
    }

    public void updateMeal(Meal meal){
        IntStream.range(0, mealsList.size()).filter(i -> mealsList.get(i).getId() == meal.getId()).forEach(i -> mealsList.set(i, meal));
    }

    public Meal getMeal(int id){
        for (Meal meal : mealsList) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    public List<Meal> getAllMeals(){
        return mealsList;
    }

}
