package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_EXCEED_CALORIES;

@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(name="users_unique_email_idx", columnNames = "email")})
public class User extends AbstractNamedEntity {
    @Column(name="email",unique = true, nullable = false)
    @Email
    @NotBlank
    @Size(max=100)
    private String email;

    @Column(name="password",nullable=false)
    @NotBlank
    @Size(min=5,max=30)
    private String password;

    @Column(name="enabled",nullable = false,columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name="registered", nullable=false,columnDefinition = "timestamp default now()")
    private Date registered = new Date();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"))
    @Column(name="role")
    @ElementCollection(fetch=FetchType.EAGER)
    private Set<Role> roles;

    @Column(name="calories_per_day",nullable = false, columnDefinition ="int default 2000")
    @Range(min=10, max=10000)
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
