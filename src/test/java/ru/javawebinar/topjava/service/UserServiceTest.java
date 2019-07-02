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
import ru.javawebinar.topjava.model.User;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.USER;

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
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
        User user=service.get(UserTestData.USER_ID);
        Assertions.assertThat(USER).isEqualToIgnoringGivenFields(user,"registered","roles");
    }

    @Test
    public void getByEmail() {
    }

    @Test
    public void update() {
    }

    @Test
    public void getAll() {
    }
}