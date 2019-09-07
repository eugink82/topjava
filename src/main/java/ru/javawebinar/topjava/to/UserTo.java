package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Range;
import ru.javawebinar.topjava.util.UserUtil;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserTo extends BaseTo implements Serializable {
    private static final long serialVersionUID=1L;
    @NotBlank
    @Size(min=2,max=20)
    private String name;

    @Email
    @NotBlank
    @Size(max=100)
    private String email;

    @NotBlank
    @Size(min=5,max=30, message="Длина пароля должна быть от 5 до 30 символов")
    private String password;

    @NotNull
    @Range(min=10,max=10000)
    private int caloriesPerDay= UserUtil.DEFAULT_EXCEED_CALORIES;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String email, String password, int caloriesPerDay) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.caloriesPerDay=caloriesPerDay;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", caloriesPerDay=" + caloriesPerDay +
                ", id=" + id +
                '}';
    }
}
