package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts="classpath:db/populateDB.sql",config=@SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {

    @Autowired
    private UserService service;
    @Test
    public void create() {
        User newUser=new User(null,"New","new@gmail.com","newpass",1555,false,new Date(), Collections.singleton(Role.ROLE_USER));
        User created=service.create(newUser);
        newUser.setId(created.getId());
        assertMatch(service.getAll(),ADMIN,newUser,USER);
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
        User user=service.get(UserTestData.USER_ID);
        assertMatch(user,USER);
    }



    @Test
    public void getByEmail() {
    }

    @Test
    public void update() {
    }

    @Test
    public void getAll() {
        List<User> users=service.getAll();
        assertMatch(users,ADMIN,USER);
    }
}