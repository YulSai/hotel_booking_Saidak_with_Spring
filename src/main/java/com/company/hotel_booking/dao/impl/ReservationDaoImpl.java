package com.company.hotel_booking.dao.impl;

import com.company.hotel_booking.dao.api.IReservationDao;
import com.company.hotel_booking.dao.api.IReservationInfoDao;
import com.company.hotel_booking.dao.api.IUserDao;
import com.company.hotel_booking.dao.connection.DataSource;
import com.company.hotel_booking.dao.entity.Reservation;
import com.company.hotel_booking.dao.entity.ReservationInfo;
import com.company.hotel_booking.exceptions.ConnectionPoolException;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.managers.SqlManager;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class object ReservationDao with implementation of CRUD operation operations
 */
@Log4j2
public class ReservationDaoImpl implements IReservationDao {
    private final DataSource dataSource;
    private final IUserDao userDao;
    private final IReservationInfoDao reservationInfoDao;

    public ReservationDaoImpl(DataSource dataSource, IUserDao userDao, IReservationInfoDao reservationInfoDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
        this.reservationInfoDao = reservationInfoDao;
    }

    @Override
    public Reservation findById(Long id) {
        log.debug("Accessing the database using the findById command. Reservation id = {}", id);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_FIND_BY_ID)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return processReservation(result, connection);
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO findById error id = {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.by.id") + id);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public Reservation findById(Long id, Connection connection) {
        log.debug("Accessing the database using the findById command. Reservation id = {}", id);
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return processReservation(result, connection);
            }
        } catch (SQLException e) {
            log.error("SQLRoomDAO findById error id = {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.by.id") + id);
        }
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        log.debug("Accessing the database using the findAll command");
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(SqlManager.SQL_RESERVATION_FIND_ALL)) {
            connection.setAutoCommit(false);

            while (result.next()) {
                reservations.add(processReservation(result, connection));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO findAll", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.all"));
        } finally {
            close(connection);
        }
        return reservations;
    }

    @Override
    public Reservation save(Reservation entity) {
        log.debug("Accessing the database using the save command");
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_CREATE,
                Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            extractedDate(entity, statement);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            connection.commit();

            if (keys.next()) {
                Long id = keys.getLong("id");
                return findById(id, connection);
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationDAO save error: " + entity, e);
        } finally {
            close(connection);
        }
        throw new DaoException(MessageManger.getMessage("msg.error.create") + entity);
    }

    @Override
    public Reservation update(Reservation entity) {
        log.debug("Accessing the database using the update command. Reservation = {}", entity);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_UPDATE)) {
            connection.setAutoCommit(false);
            extractedDate(entity, statement);
            statement.setLong(4, entity.getId());

            if (statement.executeUpdate() == 0) {
                log.error("Command update can't be executed");
                throw new DaoException(MessageManger.getMessage("msg.error.command") + entity);
            }
            connection.commit();
            return findById(entity.getId(), connection);
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO update error. Failed to update reservation = {}", entity, e);
            throw new DaoException(MessageManger.getMessage("msg.error.update") + entity);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing the database using the delete command. Reservation id = {}", id);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_DELETE)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);

            int rowsDeleted = statement.executeUpdate();
            connection.commit();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationDAO delete error id  = {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.delete") + id);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<Reservation> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_PAGE)) {
            connection.setAutoCommit(false);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                reservations.add(processReservation(result, connection));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationDAO findAllPages error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        } finally {
            close(connection);
        }
        return reservations;
    }

    @Override
    public List<Reservation> findAllPagesByUsers(int limit, long offset, Long id) {
        log.debug("Accessing the database using the findAllPagesByUsers command");
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_PAGE_BY_USER)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.setInt(2, limit);
            statement.setLong(3, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                reservations.add(processReservation(result, connection));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationDAO findAllPages error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        } finally {
            close(connection);
        }
        return reservations;
    }

    @Override
    public long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                SqlManager.SQL_RESERVATION_COUNT_RESERVATIONS)) {
            connection.setAutoCommit(false);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationDAO findRowCount error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.count"));
        } finally {
            close(connection);
        }
        throw new DaoException(MessageManger.getMessage("msg.error.find.count"));
    }

    public List<Reservation> findAllByUsers(Long id) {
        log.debug("Accessing the database using the findAllByUsers command");
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_BY_USER)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                reservations.add(processReservation(result, connection));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationDAO findAllPages error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        } finally {
            close(connection);
        }
        return reservations;
    }

    /**
     * Method fills the Reservation object with data from the database
     *
     * @param result resulting query
     * @return Reservation object
     */
    private Reservation processReservation(ResultSet result, Connection connection) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(result.getLong("id"));
        reservation.setUser(userDao.findById((result.getLong("user_id"))));
        reservation.setTotalCost(result.getBigDecimal("total_cost"));
        reservation.setStatus(Reservation.Status.valueOf(result.getString("status")));
        List<ReservationInfo> details = reservationInfoDao.findByReservationId(reservation.getId(), connection);
        reservation.setDetails(details);
        return reservation;
    }

    /**
     * Method extracts the object's fields data
     *
     * @param reservation object Reservation
     * @param statement   an object that represents a precompiled SQL statement
     */
    private void extractedDate(Reservation reservation, PreparedStatement statement) throws SQLException {
        statement.setLong(1, reservation.getUser().getId());
        statement.setBigDecimal(2, reservation.getTotalCost());
        statement.setString(3, reservation.getStatus().toString().toUpperCase());
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