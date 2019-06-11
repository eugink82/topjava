package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    private static Map<LocalDate,Integer> mapDay=new HashMap<>();
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        System.out.println("--getFilteredWithExceeded--");
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceeded.forEach(System.out::println);
        System.out.println("--getFilteredWithExceededByCycle--");
        List<UserMealWithExceed> filteredWithExceededList=getFilteredWithExceededByCycle(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceededList.forEach(System.out::println);
        System.out.println("--getFilteredWithExceeded_Recursion--");
        List<UserMealWithExceed> filteredWithExceededByRecursion=getFilteredWithExceeded_Recursion(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceededByRecursion.forEach(System.out::println);
        System.out.println("--getFilteredWithExceededInOneReturn--");
        List<UserMealWithExceed> filteredWithExceededInOneReturn=getFilteredWithExceededInOneReturn(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceededByRecursion.forEach(System.out::println);
        System.out.println("--getFilteredWithExceededInOnePass2--");
        List<UserMealWithExceed> filteredWithExceededInOnePass2=getFilteredWithExceededInOnePass2(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceededByRecursion.forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();

    }


    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream().collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream().filter(m->TimeUtil.isBetween(m.getDateTime().toLocalTime(),startTime,endTime))
                .map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(),
                        caloriesSumByDate.get(m.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate,Integer> caloriesSumPerDay=new HashMap<>();
        for(UserMeal m: mealList){
            LocalDate mealDate=m.getDateTime().toLocalDate();
            caloriesSumPerDay.put(mealDate,caloriesSumPerDay.getOrDefault(mealDate,0)+m.getCalories());
        }
        List<UserMealWithExceed> mealExceed=new ArrayList<>();
        for(UserMeal m: mealList){
           if(TimeUtil.isBetween(m.getDateTime().toLocalTime(),startTime,endTime)) {
               mealExceed.add(new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(),
                       caloriesSumPerDay.get(m.getDateTime().toLocalDate()) > caloriesPerDay));
           }
        }
        return mealExceed;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded_Recursion(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> listWithExceed=new ArrayList<>();
        mapDay.merge(mealList.get(0).getDateTime().toLocalDate(),mealList.get(0).getCalories(),(a,b)->a+b);
        if(mealList.size()>1){
            listWithExceed.addAll(getFilteredWithExceeded_Recursion(mealList.subList(1,mealList.size()),startTime,endTime,caloriesPerDay));
        }
        LocalTime time=mealList.get(0).getDateTime().toLocalTime();
        if(TimeUtil.isBetween(time,startTime,endTime)){
            listWithExceed.add(new UserMealWithExceed(mealList.get(0).getDateTime(),mealList.get(0).getDescription(),
                    mealList.get(0).getCalories(),
                    mapDay.get(mealList.get(0).getDateTime().toLocalDate())>caloriesPerDay));
        }
        return listWithExceed;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededInOneReturn(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
       return mealList.stream().collect(Collectors.groupingBy(UserMeal::getDate)).values().stream().flatMap(dayMeals->{
           boolean exceed=dayMeals.stream().mapToInt(UserMeal::getCalories).sum()>caloriesPerDay;
           return dayMeals.stream().filter(userMeals->TimeUtil.isBetween(userMeals.getTime(),startTime,endTime))
                   .map(userMeals->createWithExceed(userMeals,exceed));
       }).collect(Collectors.toList());
    }

    private static UserMealWithExceed createWithExceed(UserMeal meal, boolean exceed){
        return new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),exceed);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededInOnePass2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final class Aggregate {
            private final List<UserMeal> dailyMeals=new ArrayList<>();
            private int dailySumInCalories;

            private void accumulate(UserMeal meals){
                dailySumInCalories+=meals.getCalories();
                if(TimeUtil.isBetween(meals.getTime(),startTime,endTime)){
                    dailyMeals.add(meals);
                }
            }

            private Aggregate combine(Aggregate that){
                this.dailySumInCalories+=that.dailySumInCalories;
                this.dailyMeals.addAll(that.dailyMeals);
                return this;
            }

            private Stream<UserMealWithExceed> finisher(){
                final boolean exceed=dailySumInCalories>caloriesPerDay;
                return dailyMeals.stream().map(meals->createWithExceed(meals,exceed));
            }
        }
        Collection<Stream<UserMealWithExceed>> values=mealList.stream().collect(Collectors.groupingBy(UserMeal::getDate,
                Collector.of(Aggregate::new,Aggregate::accumulate, Aggregate::combine, Aggregate::finisher))).values();
        return values.stream().flatMap(x->x).collect(Collectors.toList());

    }



}
