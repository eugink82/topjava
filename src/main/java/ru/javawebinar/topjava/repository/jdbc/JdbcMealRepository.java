package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcMealRepository implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER=BeanPropertyRowMapper.newInstance(Meal.class);
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertMeal;

    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        ValidationUtil.validate(meal);
        MapSqlParameterSource map=new MapSqlParameterSource()
                .addValue("id",meal.getId())
                .addValue("user_id",userId)
                .addValue("date_time",meal.getDateTime())
                .addValue("description",meal.getDescription())
                .addValue("calories",meal.getCalories());
        if(meal.isNew()){
            Number key=insertMeal.executeAndReturnKey(map);
            meal.setId(key.intValue());
        }else{
            if(namedParameterJdbcTemplate.update("UPDATE meals SET date_time=:date_time," +
                    "description=:description,calories=:calories WHERE id=:id AND user_id=:user_id",map)==0){
                return null;
            }
        }
        return meal;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals=jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?",ROW_MAPPER,id,userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?",id,userId)!=0;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY date_time desc",ROW_MAPPER,userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? and date_time BETWEEN ? AND ? " +
                "ORDER BY date_time desc",ROW_MAPPER,userId,startDate,endDate);
    }
}
