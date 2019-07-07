package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ+2;


    public static final Meal MEAL_1=new Meal(MEAL_ID,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2=new Meal(MEAL_ID+1,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3=new Meal(MEAL_ID+2,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_4=new Meal(MEAL_ID+3,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_5=new Meal(MEAL_ID+4,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500);
    public static final Meal MEAL_6=new Meal(MEAL_ID+5,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 510);


    public static final Meal CREATED=new Meal(MEAL_ID+9,LocalDateTime.of(2018, Month.JULY, 5, 13, 0), "Хавка", 530);



    public static void assertMatch(Meal actual,Meal expected){
        assertThat(actual).isEqualToIgnoringGivenFields("user_id");
    }


    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected){
        assertThat(actual).isEqualTo(expected);
    }




}
/*
  (100000,'2015-05-30 10:00','Завтрак',500),
  (100000,'2015-05-30 13:00','Обед',1000),
  (100000,'2015-05-30 20:00','Ужин',500),
  (100000,'2015-05-31 10:00','Завтрак',1000),
  (100000,'2015-05-31 13:00','Обед',500),
  (100000,'2015-05-31 20:00','Ужин',510),
  (100001,'2015-05-31 21:00','Вечерний Барбекю',410),
  (100001,'2015-06-01 14:00','Сиеста',610),
  (100001,'2015-06-01 16:00','Ланч',720);

  public static final List<Meal> MEALS_USER = Arrays.asList(
            new Meal(MEAL_ID,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(MEAL_ID+1,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(MEAL_ID+2,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(MEAL_ID+3,LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(MEAL_ID+4,LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(MEAL_ID+5,LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

     public static final List<Meal> MEALS_ADMIN = Arrays.asList(
            new Meal(MEAL_ID+6,LocalDateTime.of(2015, Month.MAY, 31, 21, 0), "Вечерний Барбекю", 410),
            new Meal(MEAL_ID+7,LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Сиеста", 610),
            new Meal(MEAL_ID+8,LocalDateTime.of(2015, Month.JUNE, 1, 16, 0), "Ланч", 720)
    );

  */