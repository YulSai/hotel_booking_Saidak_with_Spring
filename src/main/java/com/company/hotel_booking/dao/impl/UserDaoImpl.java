package com.company.hotel_booking.dao.impl;

import com.company.hotel_booking.dao.api.IUserDao;
import com.company.hotel_booking.dao.entity.User;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.exceptions.RegistrationException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.managers.SqlManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class object UserDAO with implementation of CRUD operation operations
 */
@Log4j2
@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements IUserDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User findById(Long id) {
        log.debug("Accessing the database using the findById command. User's id = {}", id);
        try {
            return jdbcTemplate.queryForObject(SqlManager.SQL_USER_FIND_BY_ID, this::mapRow, id);
        } catch (DataAccessException e) {
            log.error("SQLUserDAO findById error {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.by.id") + id);
        }
    }

    @Override
    public List<User> findAll() {
        log.debug("Accessing the database using the findAll command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_USER_FIND_ALL, this::mapRow);
        } catch (DataAccessException e) {
            log.error("SQLUserDAO findAll", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.all"));
        }
    }

    @Override
    public User save(User user) {
        log.debug("Accessing the database using the create command. User = {}", user);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_USER_CREATE,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getPassword());
                statement.setString(5, user.getPhoneNumber());
                statement.setString(6, user.getRole().toString());
                statement.setString(7, user.getAvatar());
                return statement;
            }, keyHolder);
            Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return findById(id);
        } catch (DataAccessException | NullPointerException e) {
            log.error("SQLUserDAO create error ", e);
            throw new DaoException(MessageManger.getMessage("msg.error.create") + user);
        }
    }

    @Override
    public User update(User user) {
        log.debug("Accessing the database using the update command. User = {}", user);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("firstName", user.getFirstName());
            params.put("lastName", user.getLastName());
            params.put("email", user.getEmail());
            params.put("password", user.getPassword());
            params.put("phoneNumber", user.getPhoneNumber());
            params.put("role", user.getRole().toString());
            params.put("avatar", user.getAvatar());
            params.put("userId", user.getId());
            namedParameterJdbcTemplate.update(SqlManager.SQL_USER_UPDATE, params);
            return findById(user.getId());
        } catch (DataAccessException e) {
            log.error("SQLUserDAO update error. Failed to update user {}", user);
            throw new DaoException(MessageManger.getMessage("msg.error.update") + user);
        }
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing the database using the delete command. User's id = {}", id);
        try {
            return jdbcTemplate.update(SqlManager.SQL_USER_DELETE, id) == 1;
        } catch (DataAccessException e) {
            log.error("SQLUserDAO delete error {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<User> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_USER_PAGE, this::mapRow, limit, offset);
        } catch (DataAccessException e) {
            log.error("SQLUserDAO findAllPages error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        }
    }

    @Override
    public Long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        try {
            return jdbcTemplate.queryForObject(SqlManager.SQL_USER_COUNT_USERS, Long.class);
        } catch (DataAccessException e) {
            log.error("SQLUserDAO findRowCount error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.count"));
        }
    }

    @Override
    public User findUserByEmail(String email) {
        log.debug("Accessing the database using the findUserByEmail command. User's email = {}", email);
        try {
            return jdbcTemplate.queryForObject(SqlManager.SQL_USER_FIND_BY_EMAIL, this::mapRow, email);
        } catch (DataAccessException e) {
            log.error("SQLUserDAO findUserByEmail error {}", email, e);
            throw new RegistrationException(MessageManger.getMessage("msg.error.find"));
        }
    }

    /**
     * Method fills the User object with data from the database
     *
     * @param result resulting query
     * @return User object
     */
    private User mapRow(ResultSet result, int num) throws SQLException {
        User user = new User();
        user.setId(result.getLong("id"));
        user.setFirstName(result.getString("first_name"));
        user.setLastName(result.getString("last_name"));
        user.setEmail(result.getString("email"));
        user.setPassword(result.getString("password"));
        user.setPhoneNumber(result.getString("phone_number"));
        user.setRole(User.Role.valueOf((result.getString("role")).toUpperCase()));
        user.setAvatar(result.getString("avatar"));
        return user;
    }
}