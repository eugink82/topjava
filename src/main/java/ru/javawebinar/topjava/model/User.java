package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Date;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_EXCEED_CALORIES;

public class User extends AbstractNamedEntity {
    private String email;
    private String password;
    private boolean enabled = true;
    private Date registered = new Date();
    private Set<Role> roles;
    private int calories = DEFAULT_EXCEED_CALORIES;

    public User(){
    }

    public User(User user){
        this(user.getId(),user.getName(),user.getEmail(),user.getPassword(),user.getCalories(),user.isEnabled(),user.getRoles());
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, DEFAULT_EXCEED_CALORIES, true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, int calories, boolean enabled, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.calories = calories;
        this.enabled = enabled;
        this.roles = roles;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getCalories() {
        return calories;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntity user = (AbstractBaseEntity) o;
        return id!=null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id!=null ? id : 0;
    }
}
