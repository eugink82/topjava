package ru.javawebinar.topjava.service;

import org.junit.jupiter.engine.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.*;
import ru.javawebinar.topjava.service.datajpa.DataJpaMealServiceTest;
import ru.javawebinar.topjava.service.datajpa.DataJpaUserServiceTest;
import ru.javawebinar.topjava.service.jdbc.JdbcMealServiceTest;
import ru.javawebinar.topjava.service.jdbc.JdbcUserServiceTest;
import ru.javawebinar.topjava.service.jpa.JpaMealServiceTest;
import ru.javawebinar.topjava.service.jpa.JpaUserServiceTest;
import ru.javawebinar.topjava.web.RootControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtilTest;
import ru.javawebinar.topjava.web.user.InMemoryAdminRestControllerSpringTest;
import ru.javawebinar.topjava.web.user.InMemoryAdminRestControllerTest;
import ru.javawebinar.topjava.web.user.ProfileRestControllerTest;

@RunWith(JUnitPlatform.class)
@SelectClasses(
        {
          JdbcMealServiceTest.class,
          JdbcUserServiceTest.class,
          JpaMealServiceTest.class,
          JpaUserServiceTest.class,
          DataJpaMealServiceTest.class,
          DataJpaUserServiceTest.class,
          InMemoryAdminRestControllerSpringTest.class,
          InMemoryAdminRestControllerTest.class,
          RootControllerTest.class,
          ProfileRestControllerTest.class,
          JsonUtilTest.class
        }
)
public class RunAllTest {
}
