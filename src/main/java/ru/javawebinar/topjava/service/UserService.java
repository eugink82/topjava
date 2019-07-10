package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
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

    public User create(User user) {
        Assert.notNull(user,"user must not be null");
        return repository.save(user);
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id),id);
    }

    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id),id);
    }

    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email,"email must not be null");
        return checkNotFound(repository.getByEmail(email),email);
    }

    public void update(User user) {
        Assert.notNull(user,"user must not be null");
        repository.save(user);
    }

    public List<User> getAll() {
        return repository.getAll();
    }
}
