package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

public abstract class AbstractUserController {
    private final Logger LOG= LoggerFactory.getLogger(getClass());
    private UserService service;

    public User create(User user){
        LOG.info("create{}",user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id){
        LOG.info("delete{}",id);
        service.delete(id);
    }

    public User get(int id){
        LOG.info("get{}",id);
        return service.get(id);
    }

    public User getByEmail(String email){
        LOG.info("getByEmail{}",email);
        return service.getByEmail(email);
    }

    public void update(User user, int id){
        LOG.info("update{}",user);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public List<User> getAll(){
        return service.getAll();
    }
}
