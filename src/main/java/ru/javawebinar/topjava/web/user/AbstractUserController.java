package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.exception.ModificationRestrictionException;

import java.util.List;

import static ru.javawebinar.topjava.Profiles.HEROKU;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;


public abstract class AbstractUserController {
    protected final Logger LOG= LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserService service;

    @Autowired
    private UniqueMailValidator emailValidator;

    private boolean modificationRestriction;

    @Autowired
    @SuppressWarnings("deprecation")
    public void setEnvironment(Environment env) {
        this.modificationRestriction = env.acceptsProfiles(HEROKU);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(emailValidator);
    }

    public List<User> getAll(){
        LOG.info("getAll");
        return service.getAll();
    }

    public User create(User user){
        LOG.info("create{}",user);
        checkNew(user);
        return service.create(user);
    }

    public User create(UserTo userTo){
        return service.create(UserUtil.createNewFromTo(userTo));
    }

    public void delete(int id){
        LOG.info("delete{}",id);
        checkModificationAllowed(id);
        service.delete(id);
    }

    public User get(int id){
        LOG.info("get{}",id);
        return service.get(id);
    }



    public void update(User user, int id){
        LOG.info("update{}",user);
        assureIdConsistent(user, id);
        checkModificationAllowed(id);
        service.update(user);
    }

    public void update(UserTo userTo, int id){
        LOG.info("update{} with id{}",userTo,id);
        assureIdConsistent(userTo, id);
        checkModificationAllowed(id);
        service.update(userTo);
    }

    public void enable(int id,boolean enabled){
        LOG.info(enabled ? "enabled{}" : "disabled{}",id);
        checkModificationAllowed(id);
        service.enable(id,enabled);
    }

    private void checkModificationAllowed(int id){
        if(modificationRestriction && id< AbstractBaseEntity.START_SEQ+2){
            throw new ModificationRestrictionException();
        }
    }


}
