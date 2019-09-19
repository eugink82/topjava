package ru.javawebinar.topjava.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;

public class UserUtil {
    public static final int DEFAULT_EXCEED_CALORIES = 2000;

    public static User createNewFromTo(UserTo userTo){
       return new User(null,userTo.getName(), userTo.getEmail(), userTo.getPassword(),userTo.getCaloriesPerDay(), Role.ROLE_USER);
    }

    public static UserTo asTo(User user){
        return new UserTo(user.getId(),user.getName(),user.getEmail(),user.getPassword(),user.getCaloriesPerDay());
    }

    public static User updateFromTo(User user,UserTo userTo){
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        user.setCaloriesPerDay(userTo.getCaloriesPerDay());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder){
        String password=user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
