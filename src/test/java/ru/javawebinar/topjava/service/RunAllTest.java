package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                JdbcMealServiceTest.class,
                JpaMealServiceTest.class,
                DataJpaMealServiceTest.class,
                JdbcUserServiceTest.class,
                JpaUserServiceTest.class,
                DataJpaUserServiceTest.class
        }
)
public class RunAllTest {
}
