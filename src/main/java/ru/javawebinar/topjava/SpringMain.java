package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepositoryImpl;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx=new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));

        UserRepository userRepository = (UserRepository)appCtx.getBean("inMemoryUserRepository");
        UserRepository userRepository2= (UserRepository)appCtx.getBean(InMemoryUserRepositoryImpl.class);
        userRepository.getAll();
        userRepository2.getAll();
        appCtx.close();
    }
}
