package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.HasId;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

public class MealTo implements HasId {
    private Integer id;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    private boolean excess;

    public MealTo() {
    }

    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealTo mealTo = (MealTo) o;

        if (calories != mealTo.calories) return false;
        if (excess != mealTo.excess) return false;
        if (id != null ? !id.equals(mealTo.id) : mealTo.id != null) return false;
        if (dateTime != null ? !dateTime.equals(mealTo.dateTime) : mealTo.dateTime != null) return false;
        return description != null ? description.equals(mealTo.description) : mealTo.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + calories;
        result = 31 * result + (excess ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
