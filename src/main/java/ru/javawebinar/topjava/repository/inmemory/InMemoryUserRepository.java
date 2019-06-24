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
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log= LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer,User> users=new ConcurrentHashMap<>();
    private AtomicInteger counter=new AtomicInteger(0);
    public static final int USER_ID=1;
    public static final int ADMIN_ID=2;

    @Override
    public User save(User user) {
        log.debug("save{}",user);
        if(user.isNew()){
            user.setId(counter.incrementAndGet());
            return user;
        }
        return users.computeIfPresent(user.getId(),(id,oldUser)->user);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete{}",id);
        return users.remove(id)!=null;
    }

    @Override
    public User get(int id) {
        log.info("get{}",id);
        return users.get(id);
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail{}",email);
        return users.values().stream()
                .filter(u->email.equals(u.getEmail()))
                .findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        log.debug("getAll{}");
        return users.values().stream().sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail)).collect(Collectors.toList());
    }
}
