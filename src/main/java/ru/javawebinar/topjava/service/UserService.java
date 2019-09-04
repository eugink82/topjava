package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;


import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository repository;

   /*
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    } */

   @CacheEvict(value="users",allEntries = true)
    public User create(User user) {
        Assert.notNull(user,"user must not be null");
        return repository.save(user);
    }

    @CacheEvict(value="users",allEntries = true)
    public void delete(int id)  {
        checkNotFoundWithId(repository.delete(id),id);
    }

    public User get(int id)  {
        return checkNotFoundWithId(repository.get(id),id);
    }

    public User getByEmail(String email)  {
        Assert.notNull(email,"email must not be null");
        return checkNotFound(repository.getByEmail(email),email);
    }

    @CacheEvict(value="users",allEntries = true)
    public void update(User user) {
        Assert.notNull(user,"user must not be null");
        repository.save(user);
    }

    @CacheEvict(value="users",allEntries = true)
    @Transactional
    public void update(UserTo userTo) {
       User user=get(userTo.id());
       repository.save(UserUtil.updateFromTo(user,userTo));
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }

    public User userWithMeals(int id){
       return checkNotFoundWithId(repository.userWithMeals(id),id);
    }

    public void enable(int id,boolean enabled){
       User user=get(id);
       user.setEnabled(enabled);
       repository.save(user);
    }
}
