package ru.javawebinar.topjava.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.JpaUtil;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Date;

public class AbstractJpaUserServiceTest extends AbstractUserServiceTest{

    @Autowired
    protected JpaUtil jpaUtil;

    @BeforeEach
    @Override
    void setUp() throws Exception{
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
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
