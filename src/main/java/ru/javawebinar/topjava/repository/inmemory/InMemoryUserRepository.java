package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {
    private static final Logger log= LoggerFactory.getLogger(InMemoryUserRepository.class);
    public static final int USER_ID=1;
    public static final int ADMIN_ID=2;

    @Override
    public User save(User user) {
        log.debug("save{}",user);
        return super.save(user);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete{}",id);
       return super.delete(id);
    }

    @Override
    public User get(int id) {
        log.info("get{}",id);
        return super.get(id);
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail{}",email);
        return getCollection().stream()
                .filter(u->email.equals(u.getEmail()))
                .findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        log.debug("getAll{}");
        return getCollection().stream().sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail)).collect(Collectors.toList());
    }
}
