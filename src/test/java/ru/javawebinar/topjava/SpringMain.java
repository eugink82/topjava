package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.Environment;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.TestUtil.mockAuthorize;
import static ru.javawebinar.topjava.UserTestData.USER;

public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml","spring/inmemory.xml"},false)) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.POSTGRES_DB,Profiles.JPA);
            appCtx.refresh();

            mockAuthorize(USER);
            Environment env=appCtx.getBean(Environment.class);

            Arrays.stream(env.getActiveProfiles()).forEach(System.out::println);
            System.out.println(Arrays.stream(env.getActiveProfiles()).anyMatch(Profiles.JDBC::equals));
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
          //  adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            adminUserController.getAll();
            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealTo> mealfiteredWithExcess = mealController.getBetween(LocalDate.of(2015, Month.MAY, 30),
                    LocalTime.of(7, 0), LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            mealfiteredWithExcess.forEach(System.out::println);
        }


    }
}
