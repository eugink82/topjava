package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;

public class UserUtil {
    public static final int DEFAULT_EXCEED_CALORIES = 2000;

    public static User createNewFromTo(UserTo userTo){
       return new User(null,userTo.getName(), userTo.getEmail(), userTo.getPassword(), Role.ROLE_USER);
    }
}
