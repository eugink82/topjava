package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {
    private static BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validate(user);
        BeanPropertySqlParameterSource map=new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newId = insertUser.executeAndReturnKey(map);
            user.setId(newId.intValue());
            insertRoles(user);
        } else if (namedParameterJdbcTemplate.update("UPDATE users SET name=:name,email=:email,password=:password," +
                "registered=:registered,enabled=:enabled,calories_per_day=:caloriesPerDay WHERE id=:id", map) == 0) {
            return null;
        }
        deleteRoles(user);
        insertRoles(user);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        Map<Integer,Set<Role>> mapRoles=new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_roles", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                mapRoles.computeIfAbsent(rs.getInt("user_id"),user->EnumSet.noneOf(Role.class))
                        .add(Role.valueOf(rs.getString("role")));
            }
        });
        List<User> users=jdbcTemplate.query("SELECT * FROM users order by name,email", ROW_MAPPER);
        users.forEach(this::setRoles);
        return users;
    }

    private void insertRoles(User user){
        Set<Role> roles=user.getRoles();
        Iterator<Role> iterator=roles.iterator();
        jdbcTemplate.batchUpdate("INSERT INTO user_roles(user_id,role) VALUES(?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                         ps.setInt(1, user.getId());
                         ps.setString(2,iterator.next().name());
                    }

                    @Override
                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }

    private void deleteRoles(User user){
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?",user.getId());
    }

    private User setRoles(User user){
        if(user!=null){
            List<Role> roles=jdbcTemplate.queryForList("select role FROM user_roles WHERE user_id=?",Role.class,user.getId());
            user.setRoles(roles);
        }
        return user;
    }
}
