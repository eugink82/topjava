package ru.javawebinar.topjava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.datajpa.*;
import ru.javawebinar.topjava.service.jdbc.*;
import ru.javawebinar.topjava.service.jpa.*;
import ru.javawebinar.topjava.web.user.*;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                InMemoryAdminRestControllerSpringTest.class,
                InMemoryAdminRestControllerTest.class,
                JpaMealServiceTest.class,
                JpaUserServiceTest.class,
                JdbcUserServiceTest.class,
                JdbcMealServiceTest.class,
                DataJpaUserServiceTest.class,
                DataJpaMealServiceTest.class
        }
)
public class RunAllTest {
}
