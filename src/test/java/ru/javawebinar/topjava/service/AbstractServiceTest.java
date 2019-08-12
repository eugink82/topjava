package ru.javawebinar.topjava.service;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDBProfileResolver;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.TimingRules;

import java.util.Arrays;

import static ru.javawebinar.topjava.util.ValidationUtil.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver= ActiveDBProfileResolver.class)
public abstract class AbstractServiceTest {
    @ClassRule
    public static ExternalResource summary=TimingRules.SUMMARY;

    @ClassRule
    public static Stopwatch stopWatch=TimingRules.stopwatch;

    @Rule
    public ExpectedException thrown=ExpectedException.none();

    @Autowired
    protected Environment env;

    public <T extends Throwable> void validationRootCause(Runnable runnable,Class<T> exceptionClass){
        try{
            runnable.run();
            Assert.fail("Expected "+exceptionClass.getName());
        }
        catch(Exception e){
            Assert.assertThat(getRootCause(e), CoreMatchers.instanceOf(exceptionClass));
        }
    }

    protected boolean isJpaTest(){
        return Arrays.stream(env.getActiveProfiles()).anyMatch(Profiles.JPA::equals) || Arrays.stream(env.getActiveProfiles()).anyMatch(Profiles.DATAJPA::equals);
    }

}
