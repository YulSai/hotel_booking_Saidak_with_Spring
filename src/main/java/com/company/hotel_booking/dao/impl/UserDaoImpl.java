package com.company.hotel_booking.dao.impl;

import com.company.hotel_booking.dao.api.IUserDao;
import com.company.hotel_booking.dao.connection.DataSource;
import com.company.hotel_booking.dao.entity.User;
import com.company.hotel_booking.exceptions.ConnectionPoolException;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.exceptions.RegistrationException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.managers.SqlManager;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class object UserDAO with implementation of CRUD operation operations
 */
@Log4j2
public class UserDaoImpl implements IUserDao {
    private final DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(Long id) {
        log.debug("Accessing the database using the findById command. User's id = {}", id);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_USER_FIND_BY_ID)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return processUser(result);
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLUserDAO findById error {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.by.id") + id);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public User findById(Long id, Connection connection) {
        log.debug("Accessing the database using the findById command. User's id = {}", id);
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_USER_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return processUser(result);
            }
        } catch (SQLException e) {
            log.error("SQLUserDAO findById error {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.by.id") + id);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        log.debug("Accessing the database using the findAll command");
        List<User> users = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(SqlManager.SQL_USER_FIND_ALL)) {
            connection.setAutoCommit(false);

            while (result.next()) {
                users.add(processUser(result));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLUserDAO findAll", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.all"));
        } finally {
            close(connection);
        }
        return users;
    }

    @Override
    public User save(User user) {
        log.debug("Accessing the database using the create command. User = {}", user);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_USER_CREATE,
                Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            extractedDate(user, statement);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            connection.commit();

            if (keys.next()) {
                Long id = keys.getLong("id");
                return findById(id, connection);
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLUserDAO create error ", e);
        } finally {
            close(connection);
        }
        throw new DaoException(MessageManger.getMessage("msg.error.create") + user);
    }

    @Override
    public User update(User user) {
        log.debug("Accessing the database using the update command. User = {}", user);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_USER_UPDATE)) {
            connection.setAutoCommit(false);
            extractedDate(user, statement);
            statement.setLong(8, user.getId());

            if (statement.executeUpdate() == 0) {
                log.error("Command update can't be executed");
                throw new DaoException(MessageManger.getMessage("msg.error.command") + user);
            }
            connection.commit();
            return findById(user.getId(), connection);
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLUserDAO update error. Failed to update user {}", user, e);
            throw new DaoException(MessageManger.getMessage("msg.error.update") + user);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing the database using the delete command. User's id = {}", id);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_USER_DELETE)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);

            int rowsDeleted = statement.executeUpdate();
            connection.commit();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLUserDAO delete error {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.delete") + id);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<User> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        List<User> users = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_USER_PAGE)) {
            connection.setAutoCommit(false);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                users.add(processUser(result));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLUserDAO findAllPages error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        } finally {
            close(connection);
        }
        return users;
    }

    @Override
    public long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_USER_COUNT_USERS)) {
            connection.setAutoCommit(false);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLUserDAO findRowCount error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.count"));
        } finally {
            close(connection);
        }
        throw new DaoException(MessageManger.getMessage("msg.error.find.count"));
    }

    @Override
    public User findUserByEmail(String email) {
        log.debug("Accessing the database using the findUserByEmail command. User's email = {}", email);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_USER_FIND_BY_EMAIL)) {
            statement.setString(1, email);
            connection.setAutoCommit(false);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return processUser(result);
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLUserDAO findUserByEmail error {}", email, e);
            throw new RegistrationException(MessageManger.getMessage("msg.error.find"));
        } finally {
            close(connection);
        }
        return null;
    }

    /**
     * Method fills the User object with data from the database
     *
     * @param result resulting query
     * @return User object
     */
    private User processUser(ResultSet result) throws SQLException {
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

    /**
     * Method extracts the object's fields data
     *
     * @param user      object User
     * @param statement an object that represents a precompiled SQL statement
     */
    private void extractedDate(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getPhoneNumber());
        statement.setString(6, user.getRole().toString());
        statement.setString(7, user.getAvatar());
    }

    /**
     * Method rolls back to the last commit state
     *
     * @param connection Connection
     */
    private void rollback(Connection connection) {
        try {
            connection.setAutoCommit(true);
            connection.rollback();
        } catch (SQLException ex) {
            throw new ConnectionPoolException(MessageManger.getMessage("msg.error.rollback"), ex);
        }
    }

    /**
     * Method closes connection
     *
     * @param connection Connection
     */
    private void close(Connection connection) {
        try {
            log.debug("Connection was 'close'");
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionPoolException(MessageManger.getMessage("msg.no.close"), e);
        }
    }
}