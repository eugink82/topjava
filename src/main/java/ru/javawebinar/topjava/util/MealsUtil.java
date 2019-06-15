package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

public class MealsUtil {
    public static final List<Meal> MEAL_LIST = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );
    public static void main(String[] args) {

        Collection<MealTo> mealsWithExcess = getFilteredWithExcess(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExcess.forEach(System.out::println);

        System.out.println(getFilteredWithExcessByCycle(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
       // System.out.println(getFilteredWithExcessInOnePass(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExcessInOnePass2(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
       // System.out.println(MEAL_LIST.get(2).getDate()+" "+MEAL_LIST.get(2).getTime());
    }

    public static Collection<MealTo> getWithExcess(Collection<Meal> MEAL_LIST,int caloriesPerDay){
        return getFilteredWithExcess(MEAL_LIST,LocalTime.MIN,LocalTime.MAX,caloriesPerDay);
    }

    public static Collection<MealTo> getFilteredWithExcess(Collection<Meal> MEAL_LIST, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = MEAL_LIST.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return MEAL_LIST.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static Collection<MealTo> getFilteredWithExcessByCycle(Collection<Meal> MEAL_LIST, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        MEAL_LIST.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final Collection<MealTo> mealsWithExcess = new ArrayList<>();
        MEAL_LIST.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExcess.add(createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExcess;
    }

//    public static List<MealTo> getFilteredWithExcessInOnePass(Collection<Meal> MEAL_LIST, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
//        Collection<Collection<Meal>> Collection = MEAL_LIST.stream()
//                .collect(Collectors.groupingBy(Meal::getDate)).values();
//
//        return Collection.stream().flatMap(dayMeals -> {
//            boolean excess = dayMeals.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
//            return dayMeals.stream().filter(meal ->
//                    TimeUtil.isBetween(meal.getTime(), startTime, endTime))
//                    .map(meal -> createWithExcess(meal, excess));
//        }).collect(toList());
//    }

    public static Collection<MealTo> getFilteredWithExcessInOnePass2(Collection<Meal> MEAL_LIST, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final class Aggregate {
            private final Collection<Meal> dailyMeals = new ArrayList<>();
            private int dailySumOfCalories;

            private void accumulate(Meal meal) {
                dailySumOfCalories += meal.getCalories();
                if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    dailyMeals.add(meal);
                }
            }

            // never invoked if the upstream is sequential
            private Aggregate combine(Aggregate that) {
                this.dailySumOfCalories += that.dailySumOfCalories;
                this.dailyMeals.addAll(that.dailyMeals);
                return this;
            }

            private Stream<MealTo> finisher() {
                final boolean excess = dailySumOfCalories > caloriesPerDay;
                return dailyMeals.stream().map(meal -> createWithExcess(meal, excess));
            }
        }

        Collection<Stream<MealTo>> values = MEAL_LIST.stream()
                .collect(Collectors.groupingBy(Meal::getDate,
                        Collector.of(Aggregate::new, Aggregate::accumulate, Aggregate::combine, Aggregate::finisher))
                ).values();

        return values.stream().flatMap(identity()).collect(toList());
    }

    private static MealTo createWithExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getId(),meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}