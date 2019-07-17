package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class AdminRestController extends AbstractUserController{

    public User create(User user){
        LOG.info("create{}",user);
        checkNew(user);
        return service.create(user);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public User get(int id) {
        return super.get(id);
    }

    public User getByEmail(String email){
        LOG.info("getByEmail{}",email);
        return service.getByEmail(email);
    }

    @Override
    public void update(User user, int id) {
        super.update(user, id);
    }

    public List<User> getAll(){
        return service.getAll();
    }
}
