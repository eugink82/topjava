package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final List<Meal> MEAL_LIST = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
    );
    public static final int DEFAULT_EXCEED_CALORIES=2000;

    public static List<MealTo> getWithExcess(Collection<Meal> meals, int calories){
        return getFilteredWithExcess(meals,calories,meal->true);
    }

    public static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, int calories, Predicate<Meal> filter){
        Map<LocalDate,Integer> caloriesPerDay=meals.stream().collect(Collectors.groupingBy(Meal::getDate,Collectors.summingInt(Meal::getCalories)));
        return meals.stream().filter(filter)
                .map(meal->createMealToExcess(meal,caloriesPerDay.get(meal.getDate())>calories)).collect(Collectors.toList());
    }

    public static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, @Nullable LocalTime startTime, @Nullable LocalTime endTime, int calories){
        return getFilteredWithExcess(meals,calories,meal->Util.isBetween(meal.getTime(),startTime,endTime));
    }

    private static MealTo createMealToExcess(Meal meal,boolean excess){
        return new MealTo(meal.getId(),meal.getDateTime(),meal.getDescription(),meal.getCalories(),excess);
    }

}
