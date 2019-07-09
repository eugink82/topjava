package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.ADMIN;


@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    public void init() {
        entranceMap.clear();
        entranceMap.put(UserTestData.USER_ID, USER);
        entranceMap.put(UserTestData.ADMIN_ID, ADMIN);
    }



    @Override
    public User getByEmail(String email) {
        log.info("getByEmail{}", email);
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        log.debug("getAll{}");
        return getCollection().stream().sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail)).collect(Collectors.toList());
    }

}
