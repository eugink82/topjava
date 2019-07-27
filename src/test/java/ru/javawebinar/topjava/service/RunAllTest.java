package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
          JdbcMealServiceTest.class,
          JdbcUserServiceTest.class,
          JpaMealServiceTest.class,
          JpaUserServiceTest.class,
          DataJpaMealServiceTest.class,
          DataJpaUserServiceTest.class,
        }
)
public class RunAllTest {
}
