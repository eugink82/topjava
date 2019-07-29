package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDBProfileResolver;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;


public abstract class AbstractUserServiceTest extends AbstractServiceTest{

    @Autowired
    protected UserService service;

    @Autowired
    protected CacheManager cacheManager;

    @Before
    public void setUp() {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() {
        User newUser = new User(null, "New", "new@gmail.com", "newpass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(newUser);
        newUser.setId(created.getId());
        assertMatch(created,newUser);
        assertMatch(service.getAll(), ADMIN, newUser, USER);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailUser() {
        service.create(new User(null, "newUser", "user@yandex.ru", "qwerty", 1600, false, new Date(), Collections.singleton(Role.ROLE_USER)));
    }

    @Test
    public void delete() {
        service.delete(100000);
        assertMatch(service.getAll(), ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotExist() {
        service.delete(1);
    }

    @Test
    public void get() {
        User user = service.get(UserTestData.USER_ID);
        assertMatch(user, USER);
    }

    @Test(expected = NotFoundException.class)
    public void getNotExistsUser() {
        User user = service.get(1);
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail("user@yandex.ru");
        assertMatch(user, USER);
    }

    @Test
    public void update() {
        User updUser = new User(USER);
        updUser.setName("UpdUser");
        updUser.setCaloriesPerDay(780);
        service.update(updUser);
        assertMatch(service.get(USER_ID), updUser);
    }

    @Test
    public void getAll() {
        List<User> users = service.getAll();
        assertMatch(users, ADMIN, USER);
    }

    @Test
    public void testValidation() throws Exception {
        validationRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", 9, true, new Date(), Collections.emptySet())), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", 10001, true, new Date(), Collections.emptySet())), ConstraintViolationException.class);
    }
}