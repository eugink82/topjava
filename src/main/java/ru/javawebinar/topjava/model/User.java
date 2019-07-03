package ru.javawebinar.topjava.model;

import org.springframework.util.CollectionUtils;

import java.util.*;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_EXCEED_CALORIES;

public class User extends AbstractNamedEntity {
    private String email;
    private String password;
    private boolean enabled = true;
    private Date registered = new Date();
    private Collection<Role> roles;
    private int calories = DEFAULT_EXCEED_CALORIES;

    public User() {
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCaloriesPerDay(), user.isEnabled(), user.getRegistered(), user.getRoles());
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, DEFAULT_EXCEED_CALORIES, true, new Date(), EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, int calories, boolean enabled, Date registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.calories = calories;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getCaloriesPerDay() {
        return calories;
    }

    public void setCaloriesPerDay(int calories) {
        this.calories = calories;
    }

    private void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", roles=" + roles +
                ", calories=" + calories +
                ", name='" + name + '\'' +
                '}';
    }
}
