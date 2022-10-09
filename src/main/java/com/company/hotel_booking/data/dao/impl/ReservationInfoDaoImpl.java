package com.company.hotel_booking.data.dao.impl;

import com.company.hotel_booking.data.dao.api.IReservationInfoDao;
import com.company.hotel_booking.data.dao.api.IRoomDao;
import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.managers.SqlManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class object ReservationInfoDao with implementation of CRUD operation operations
 */
@Log4j2
@Repository
@RequiredArgsConstructor
public class ReservationInfoDaoImpl implements IReservationInfoDao {
    private final JdbcTemplate jdbcTemplate;
    private final IRoomDao roomDao;

    @Override
    public ReservationInfo findById(Long id) {
        log.debug("Accessing the database using the findById command. ReservationInfo id = {}", id);
        try {
            return jdbcTemplate.queryForObject(SqlManager.SQL_RESERVATION_INFO_FIND_BY_ID, this::mapRow, id);
        } catch (DataAccessException e) {
            log.error("SQLReservationInfo findById error: = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.by.id") + id);
        }
    }

    @Override
    public List<ReservationInfo> findAll() {
        log.debug("Accessing the database using the findAll command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_RESERVATION_INFO_FIND_ALL, this::mapRow);
        } catch (DataAccessException e) {
            log.error("SQLReservationInfoDAO findAll", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.all"));
        }
    }

    @Override
    public ReservationInfo save(ReservationInfo reservationInfo) {
        log.debug("Accessing the database using the save command");
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_RESERVATION_INFO_CREATE,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, reservationInfo.getReservationId());
                statement.setLong(2, reservationInfo.getRoom().getId());
                statement.setDate(3, java.sql.Date.valueOf(reservationInfo.getCheckIn()));
                statement.setDate(4, java.sql.Date.valueOf(reservationInfo.getCheckOut()));
                statement.setLong(5, reservationInfo.getNights());
                statement.setBigDecimal(6, reservationInfo.getRoomPrice());
                return statement;
            }, keyHolder);
            Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return findById(id);
        } catch (DataAccessException | NullPointerException e) {
            log.error("SQLReservationInfoDAO save error: " + reservationInfo, e);
            throw new DaoException(MessageManager.getMessage("msg.error.create") + reservationInfo);
        }
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
    public ReservationInfo update(ReservationInfo reservationInfo) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing the database using the delete command. ReservationInfo id = {}", id);
        try {
            return jdbcTemplate.update(SqlManager.SQL_RESERVATION_INFO_DELETE, id) == 1;
        } catch (DataAccessException e) {
            log.error("SQLReservationInfoDAO delete error id  = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<ReservationInfo> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_RESERVATION_INFO_PAGE, this::mapRow, limit, offset);
        } catch (DataAccessException e) {
            log.error("SQLReservationInfoDAO findAllPages error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    public Long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        try {
            return jdbcTemplate.queryForObject(SqlManager.SQL_RESERVATION_INFO_COUNT_RESERVATIONS_INFO, Long.class);
        } catch (DataAccessException e) {
            log.error("SQLReservationInfoDAO findRowCount error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.count"));
        }
    }

    @Override
    public List<ReservationInfo> findByReservationId(Long id) {
        log.debug("Accessing the database using the findByReservationId command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_RESERVATION_INFO_FIND_BY_RESERVATION_ID, this::mapRow, id);
        } catch (DataAccessException e) {
            log.error("SQLReservationInfoDAO findByReservationId", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    /**
     * Method fills the ReservationInfo object with data from the database
     *
     * @param result resulting query
     * @return ReservationInfo object
     */
    private ReservationInfo mapRow(ResultSet result, int num) throws SQLException {
        ReservationInfo reservationInfo = new ReservationInfo();
        reservationInfo.setId(result.getLong("id"));
        reservationInfo.setReservationId(result.getLong("reservation_id"));
        Room room = roomDao.findById(result.getLong("room_id"));
        reservationInfo.setRoom(room);
        LocalDate checkIn = LocalDate.parse(result.getString("check_in"));
        LocalDate checkOut = LocalDate.parse(result.getString("check_out"));
        reservationInfo.setCheckIn(checkIn);
        reservationInfo.setCheckOut(checkOut);
        reservationInfo.setNights(ChronoUnit.DAYS.between(checkIn, checkOut));
        reservationInfo.setRoomPrice(result.getBigDecimal("room_price"));
        return reservationInfo;
    }
}