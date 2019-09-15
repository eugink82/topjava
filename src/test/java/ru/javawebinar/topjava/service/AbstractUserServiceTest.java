package ru.javawebinar.topjava.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.topjava.UserTestData.*;


public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Autowired
    protected CacheManager cacheManager;

    private JpaUtil jpaUtil;

    @Autowired(required = false)
    private void setJpaUtil(JpaUtil jpaUtil){
        Assert.isTrue(!isJpaTest() || jpaUtil!=null,"JpaUtil is missed in JPA profile");
        this.jpaUtil=jpaUtil;
    }

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        if(isJpaTest()){
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    void create() {
        User newUser = new User(null, "New", "new@gmail.com", "newpass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(created, newUser);
        assertMatch(service.getAll(), ADMIN, newUser, USER);
    }

    @Test
    void duplicateMailUser() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "newUser", "user@yandex.ru", "qwerty", 1600, false, new Date(), Collections.singleton(Role.ROLE_USER))));
    }

    @Test
    void delete() {
        service.delete(100000);
        assertMatch(service.getAll(), ADMIN);
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotFoundException.class,()->
        service.delete(1));
    }

    @Test
    void get() {
        User user = service.get(UserTestData.ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test
    void getNotExistsUser() {
        assertThrows(NotFoundException.class,()->
         service.get(1));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    void update() {
        User updUser = new User(USER);
        updUser.setName("UpdUser");
        updUser.setCaloriesPerDay(780);
        updUser.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(new User(updUser));
        assertMatch(service.get(USER_ID), updUser);
    }

    @Test
    void getAll() {
        List<User> users = service.getAll();
        assertMatch(users, ADMIN, USER);
    }

    @Test
    void enable(){
        service.enable(USER_ID,false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID,true);
        assertTrue(service.get(USER_ID).isEnabled());
    }

    @Test
    void createWithException() throws Exception {
        validationRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", 9, true, new Date(), Collections.emptySet())), ConstraintViolationException.class);
        validationRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", 10001, true, new Date(), Collections.emptySet())), ConstraintViolationException.class);
    }


}