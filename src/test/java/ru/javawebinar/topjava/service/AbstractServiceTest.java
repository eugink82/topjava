package ru.javawebinar.topjava.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.javawebinar.topjava.ActiveDBProfileResolver;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.TimingExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.topjava.util.ValidationUtil.*;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
//@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDBProfileResolver.class)
@ExtendWith(TimingExtension.class)
abstract class AbstractServiceTest {

    @Autowired
    protected Environment env;

    <T extends Throwable> void validationRootCause(Runnable runnable, Class<T> exceptionClass) {
        assertThrows(exceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }

    protected boolean isJpaTest() {
        return Arrays.stream(env.getActiveProfiles()).anyMatch(Profiles.JPA::equals) || Arrays.stream(env.getActiveProfiles()).anyMatch(Profiles.DATAJPA::equals);
    }

}
