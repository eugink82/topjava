package ru.javawebinar.topjava;

import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TimingRules {
    private static final Logger LOG = LoggerFactory.getLogger("result");
    private static StringBuilder timeWorkTests=new StringBuilder();
    private static String DELIMITER="-".repeat(100);

    public static Stopwatch stopwatch=new Stopwatch() {

        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            timeWorkTests.append(result);
            LOG.info(result + " ms\n");
            // String timeTest="Тест: "+description.getMethodName()+", время работы: "+ TimeUnit.NANOSECONDS.toMillis(nanos)+"ms";
            // timeWorkTests.append(System.lineSeparator()).append(timeTest);
        }
    };

    public static final ExternalResource SUMMARY=new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            timeWorkTests.setLength(0);
        }

        @Override
        protected void after() {
            LOG.info("\n" +DELIMITER+
                    "\nTest                      Duration, ms" +
                    "\n" +DELIMITER+"\n"+
                    timeWorkTests+
                    DELIMITER+"\n");
        }
    };
}
