package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDBProfileResolver;

import java.util.concurrent.TimeUnit;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver= ActiveDBProfileResolver.class)
public abstract class AbstractServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger("result");
    private static StringBuilder timeWorkTests=new StringBuilder();

    @Rule
    public ExpectedException thrown=ExpectedException.none();

    @Rule
    public Stopwatch stopwatch=new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            timeWorkTests.append(result);
            LOG.info(result + " ms\n");
            // String timeTest="Тест: "+description.getMethodName()+", время работы: "+ TimeUnit.NANOSECONDS.toMillis(nanos)+"ms";
            // timeWorkTests.append(System.lineSeparator()).append(timeTest);
        }

        @Override
        protected void failed(long nanos, Throwable e, Description description) {
            String result = String.format("\n%-25s %-25s %7d", description.getMethodName(),e.getMessage(), TimeUnit.NANOSECONDS.toMillis(nanos));
            timeWorkTests.append(result);
            LOG.info(result + " ms\n");
        }
    };



    @AfterClass
    public static void fullResults(){
        LOG.info("\n----------------------------" +
                "\nTest            Duration, ms" +
                "\n----------------------------" +
                timeWorkTests+
                "\n----------------------------");
        //LOG.info(timeWorkTests+"");
    }
}
