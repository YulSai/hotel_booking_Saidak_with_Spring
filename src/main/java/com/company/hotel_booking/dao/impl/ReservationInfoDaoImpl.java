package com.company.hotel_booking.dao.impl;

import com.company.hotel_booking.dao.api.IReservationInfoDao;
import com.company.hotel_booking.dao.api.IRoomDao;
import com.company.hotel_booking.dao.connection.DataSource;
import com.company.hotel_booking.dao.entity.ReservationInfo;
import com.company.hotel_booking.dao.entity.Room;
import com.company.hotel_booking.exceptions.ConnectionPoolException;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.managers.SqlManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class object ReservationInfoDao with implementation of CRUD operation operations
 */
@Log4j2
@Repository
@RequiredArgsConstructor
public class ReservationInfoDaoImpl implements IReservationInfoDao {
    private final DataSource dataSource;
    private final IRoomDao roomDao;

    @Override
    public ReservationInfo findById(Long id) {
        log.debug("Accessing the database using the findById command. ReservationInfo id = {}", id);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_INFO_FIND_BY_ID)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return processReservationInfo(result, connection);
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationInfo findById error: = {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.by.id") + id);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public ReservationInfo findById(Long id, Connection connection) {
        log.debug("Accessing the database using the findById command. ReservationInfo id = {}", id);
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_INFO_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return processReservationInfo(result, connection);
            }
        } catch (SQLException e) {
            log.error("SQLReservationInfo findById error: = {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.by.id") + id);
        }
        return null;
    }

    @Override
    public List<ReservationInfo> findAll() {
        log.debug("Accessing the database using the findAll command");
        List<ReservationInfo> reservationsInfo = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(SqlManager.SQL_RESERVATION_INFO_FIND_ALL)) {
            connection.setAutoCommit(false);

            while (result.next()) {
                reservationsInfo.add(processReservationInfo(result, connection));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationInfoDAO findAll", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.all"));
        } finally {
            close(connection);
        }
        return reservationsInfo;
    }

    @Override
    public ReservationInfo save(ReservationInfo entity) {
        log.debug("Accessing the database using the save command");
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_INFO_CREATE,
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
            log.error("SQLReservationInfoDAO save error: " + entity, e);
        } finally {
            close(connection);
        }
        throw new DaoException(MessageManger.getMessage("msg.error.create") + entity);
    }

    @Override
    public List<ReservationInfo> processBookingInfo(Map<Long, Long> booking, LocalDate checkIn,
                                                    LocalDate checkOut, ReservationDto reservation) {
        log.debug("Accessing the database using the processBookingInfo command");
        List<ReservationInfo> reservationsInfo = new ArrayList<>();
        booking.forEach((roomId, quantity) -> {
            ReservationInfo info = new ReservationInfo();
            info.setReservationId(reservation.getId());
            Room room = roomDao.findById(roomId);
            info.setRoom(room);
            info.setCheckIn(checkIn);
            info.setCheckOut(checkOut);
            info.setNights(ChronoUnit.DAYS.between(checkIn, checkOut));
            info.setRoomPrice(room.getPrice());
            reservationsInfo.add(info);
            save(info);
        });
        return reservationsInfo;
    }


    @Override
    public ReservationInfo update(ReservationInfo entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing the database using the delete command. ReservationInfo id = {}", id);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_INFO_DELETE)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);

            int rowsDeleted = statement.executeUpdate();
            connection.commit();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationInfoDAO delete error id  = {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.delete") + id);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<ReservationInfo> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        List<ReservationInfo> reservationInfos = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_INFO_PAGE)) {
            connection.setAutoCommit(false);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                reservationInfos.add(processReservationInfo(result, connection));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationInfoDAO findAllPages error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        } finally {
            close(connection);
        }
        return reservationInfos;
    }

    @Override
    public long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                SqlManager.SQL_RESERVATION_INFO_COUNT_RESERVATIONS_INFO)) {
            connection.setAutoCommit(false);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationInfoDAO findRowCount error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.count"));
        } finally {
            close(connection);
        }
        throw new DaoException(MessageManger.getMessage("msg.error.find.count"));
    }

    @Override
    public List<ReservationInfo> findByReservationId(Long id) {
        log.debug("Accessing the database using the findByReservationId command");
        List<ReservationInfo> reservationsInfo = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                SqlManager.SQL_RESERVATION_INFO_FIND_BY_RESERVATION_ID)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                reservationsInfo.add(processReservationInfo(result, connection));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLReservationInfoDAO findByReservationId", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        } finally {
            close(connection);
        }
        return reservationsInfo;
    }

    @Override
    public List<ReservationInfo> findByReservationId(Long id, Connection connection) {
        log.debug("Accessing the database using the findByReservationId command");
        List<ReservationInfo> reservationsInfo = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                SqlManager.SQL_RESERVATION_INFO_FIND_BY_RESERVATION_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                reservationsInfo.add(processReservationInfo(result, connection));
            }
        } catch (SQLException e) {
            log.error("SQLReservationInfoDAO findByReservationId", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        }
        return reservationsInfo;
    }

    /**
     * Method fills the ReservationInfo object with data from the database
     *
     * @param result resulting query
     * @return ReservationInfo object
     */
    private ReservationInfo processReservationInfo(ResultSet result, Connection connection) throws SQLException {
        ReservationInfo reservationInfo = new ReservationInfo();
        reservationInfo.setId(result.getLong("id"));
        reservationInfo.setReservationId(result.getLong("reservation_id"));
        Room room = roomDao.findById(result.getLong("room_id"), connection);
        reservationInfo.setRoom(room);
        LocalDate checkIn = LocalDate.parse(result.getString("check_in"));
        LocalDate checkOut = LocalDate.parse(result.getString("check_out"));
        reservationInfo.setCheckIn(checkIn);
        reservationInfo.setCheckOut(checkOut);
        reservationInfo.setNights(ChronoUnit.DAYS.between(checkIn, checkOut));
        reservationInfo.setRoomPrice(result.getBigDecimal("room_price"));
        return reservationInfo;
    }

    /**
     * Method extracts the object's fields data
     *
     * @param reservationInfo object ReservationInfo
     * @param statement       an object that represents a precompiled SQL statement
     */
    private void extractedDate(ReservationInfo reservationInfo, PreparedStatement statement) throws SQLException {
        statement.setLong(1, reservationInfo.getReservationId());
        statement.setLong(2, reservationInfo.getRoom().getId());
        statement.setDate(3, java.sql.Date.valueOf(reservationInfo.getCheckIn()));
        statement.setDate(4, java.sql.Date.valueOf(reservationInfo.getCheckOut()));
        statement.setLong(5, reservationInfo.getNights());
        statement.setBigDecimal(6, reservationInfo.getRoomPrice());
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