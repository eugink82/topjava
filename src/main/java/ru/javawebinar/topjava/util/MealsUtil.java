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

    public static final int DEFAULT_EXCEED_CALORIES = 2000;

    private MealsUtil() {
    }

    public static List<MealTo> getWithExcess(Collection<Meal> meals, int calories) {
        return getFilteredWithExcess(meals, calories, meal -> true);
    }

    public static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, int calories, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesPerDay = meals.stream().collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
        return meals.stream().filter(filter)
                .map(meal -> createMealToExcess(meal, caloriesPerDay.get(meal.getDate()) > calories)).collect(Collectors.toList());
    }

    public static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, @Nullable LocalTime startTime, @Nullable LocalTime endTime, int calories) {
        return getFilteredWithExcess(meals, calories, meal -> Util.isBetween(meal.getTime(), startTime, endTime));
    }

    public static MealTo createMealToExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

}
