package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;

@Controller
public class ProfileRestController extends AbstractUserController{
    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public User get(int id) {
        return super.get(id);
    }

    @Override
    public void update(User user, int id) {
        super.update(user, id);
    }
}
