package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;


public abstract class AbstractUserController {
    protected final Logger LOG= LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserService service;



    public void delete(int id){
        LOG.info("delete{}",id);
        service.delete(id);
    }

    public User get(int id){
        LOG.info("get{}",id);
        return service.get(id);
    }



    public void update(User user, int id){
        LOG.info("update{}",user);
        assureIdConsistent(user, id);
        service.update(user);
    }


}
