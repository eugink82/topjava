package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
       List<UserMealWithExceed> list=getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
       for(UserMealWithExceed u: list){
           System.out.println("LocalDatTime: "+u.getDateTime()+", description: "+u.getDescription()+", calories: "+u.getCalories()+", exceed: "+u.isExceed());
       }
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
                      /*1-ый вариант -циклы*/
        List<UserMealWithExceed> listExceed = new ArrayList<>();
        Map<LocalDate, Integer> map = new HashMap<>();
        List<UserMeal> mealListFiltered = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            LocalTime time = meal.getDateTime().toLocalTime();
            map.merge(date, meal.getCalories(), (a, b) -> a+b);
            if (TimeUtil.isBetween(time, startTime, endTime)) {
                mealListFiltered.add(meal);
            }
        }
        for (UserMeal meal : mealListFiltered) {
            LocalDate date = meal.getDateTime().toLocalDate();
            listExceed.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), (map.get(date) > caloriesPerDay)));
        }
        return listExceed;

                   /*2-ой вариант через Stream API*/
//        List<UserMeal> mealListFiltered=mealList.stream().filter(x->x.getDateTime().toLocalTime().compareTo(startTime) >= 0 && x.getDateTime().toLocalTime().compareTo(endTime) <= 0).
//                collect(Collectors.toList());
//        List<UserMealWithExceed> listUserMealWithExceed=new ArrayList<>();
//        Map<LocalDate, Integer> mapCalOnDay = mealList.stream().collect(Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, (y, a) -> y + a));
//        return mealListFiltered.stream().map(x->new UserMealWithExceed(x.getDateTime(),x.getDescription(),x.getCalories(),
//                (mapCalOnDay.get(x.getDateTime().toLocalDate())>caloriesPerDay))).collect(Collectors.toList());
    }

}
