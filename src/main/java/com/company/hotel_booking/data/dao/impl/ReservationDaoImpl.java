package com.company.hotel_booking.data.dao.impl;

import com.company.hotel_booking.data.dao.api.IReservationDao;
import com.company.hotel_booking.data.dao.api.IReservationInfoDao;
import com.company.hotel_booking.data.dao.api.IUserDao;
import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.SqlManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class object ReservationDao with implementation of CRUD operation operations
 */
@Log4j2
@Repository
@RequiredArgsConstructor
public class ReservationDaoImpl implements IReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final IUserDao userDao;
    private final IReservationInfoDao reservationInfoDao;

    @Override
    public Reservation findById(Long id) {
        log.debug("Accessing the database using the findById command. Reservation id = {}", id);
        try {
            return jdbcTemplate.queryForObject(SqlManager.SQL_RESERVATION_FIND_BY_ID, this::mapRow, id);
        } catch (DataAccessException e) {
            log.error("SQLRoomDAO findById error id = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.by.id") + id);
        }
    }

    @Override
    public List<Reservation> findAll() {
        log.debug("Accessing the database using the findAll command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_RESERVATION_FIND_ALL, this::mapRow);
        } catch (DataAccessException e) {
            log.error("SQLRoomDAO findAll", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.all"));
        }
    }

    @Override
    public Reservation save(Reservation reservation) {
        log.debug("Accessing the database using the save command");
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_CREATE,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, reservation.getUser().getId());
                statement.setBigDecimal(2, reservation.getTotalCost());
                statement.setString(3, reservation.getStatus().toString().toUpperCase());
                return statement;
            }, keyHolder);
            Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return findById(id);
        } catch (DataAccessException | NullPointerException e) {
            log.error("SQLReservationDAO save error: " + reservation, e);
            throw new DaoException(MessageManager.getMessage("msg.error.create") + reservation);
        }
    }

    @Override
    public Reservation update(Reservation reservation) {
        log.debug("Accessing the database using the update command reservation");
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", reservation.getUser().getId());
            params.put("totalCost", reservation.getTotalCost());
            params.put("status", reservation.getStatus().toString().toUpperCase());
            params.put("reservationId", reservation.getId());
            namedParameterJdbcTemplate.update(SqlManager.SQL_RESERVATION_UPDATE, params);
            return findById(reservation.getId());
        } catch (DataAccessException e) {
            log.error("SQLRoomDAO update error. Failed to update reservation = {}", reservation, e);
            throw new DaoException(MessageManager.getMessage("msg.error.update") + reservation);
        }
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing the database using the delete command. Reservation id = {}", id);
        try {
            return jdbcTemplate.update(SqlManager.SQL_RESERVATION_DELETE, id) == 1;
        } catch (DataAccessException e) {
            log.error("SQLReservationDAO delete error id  = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<Reservation> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_RESERVATION_PAGE, this::mapRow, limit, offset);
        } catch (DataAccessException e) {
            log.error("SQLReservationDAO findAllPages error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    public List<Reservation> findAllPagesByUsers(int limit, long offset, Long id) {
        log.debug("Accessing the database using the findAllPagesByUsers command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_RESERVATION_PAGE_BY_USER, this::mapRow, id, limit, offset);
        } catch (DataAccessException e) {
            log.error("SQLReservationDAO findAllPages error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    public Long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        try {
            return jdbcTemplate.queryForObject(SqlManager.SQL_RESERVATION_COUNT_RESERVATIONS, Long.class);
        } catch (DataAccessException e) {
            log.error("SQLReservationDAO findRowCount error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.count"));
        }
    }

    public List<Reservation> findAllByUsers(Long id) {
        log.debug("Accessing the database using the findAllByUsers command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_RESERVATION_BY_USER, this::mapRow, id);
        } catch (DataAccessException e) {
            log.error("SQLReservationDAO findAllPages error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    /**
     * Method fills the Reservation object with data from the database
     *
     * @param result resulting query
     * @return Reservation object
     */
    private Reservation mapRow(ResultSet result, int num) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(result.getLong("id"));
        reservation.setUser(userDao.findById((result.getLong("user_id"))));
        reservation.setTotalCost(result.getBigDecimal("total_cost"));
        reservation.setStatus(Reservation.Status.valueOf(result.getString("status")));
        List<ReservationInfo> details = reservationInfoDao.findByReservationId(reservation.getId());
        reservation.setDetails(details);
        return reservation;
    }
}