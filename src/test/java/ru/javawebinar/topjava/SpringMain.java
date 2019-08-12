package ru.javawebinar.topjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.jdbc.JdbcUserRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            JdbcTemplate jdbcTemplate=(JdbcTemplate)appCtx.getBean("jdbcTemplate");
            BeanPropertyRowMapper<User> ROW_MAPPER=BeanPropertyRowMapper.newInstance(User.class);
            List<Role> roles=jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id=?",Role.class,100001);
            for(Role r: roles){
                System.out.println(r.name());
            }
            Map<Integer, Set<Role>> map=new HashMap<>();
            jdbcTemplate.query("SELECT * FROM user_roles", new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    map.computeIfAbsent(rs.getInt("user_id"), userId -> EnumSet.noneOf(Role.class))
                            .add(Role.valueOf(rs.getString("role")));
                }
            });

//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
//            System.out.println();
//
//            MealRestController mealController = appCtx.getBean(MealRestController.class);
//            List<MealTo> filteredMealsWithExcess =
//                    mealController.getBetween(
//                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
//                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
//            filteredMealsWithExcess.forEach(System.out::println);
        }
    }
}
