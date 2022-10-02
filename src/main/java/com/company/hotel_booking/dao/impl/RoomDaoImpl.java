package com.company.hotel_booking.dao.impl;

import com.company.hotel_booking.dao.api.IRoomDao;
import com.company.hotel_booking.dao.entity.Room;
import com.company.hotel_booking.exceptions.DaoException;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class object RoomDAO with implementation of CRUD operation operations
 */
@Log4j2
@Repository
@RequiredArgsConstructor
public class RoomDaoImpl implements IRoomDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Room findById(Long id) {
        log.debug("Accessing the database using the findById  command. Room id = {}", id);
        try {
            return jdbcTemplate.queryForObject(SqlManager.SQL_ROOM_FIND_BY_ID, this::mapRow, id);
        } catch (DataAccessException e) {
            log.error("SQLRoomDAO findById error id = {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.by.id") + id);
        }
    }

    public List<Room> findAll() {
        log.debug("Accessing the database using the findAll command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_ROOM_FIND_ALL, this::mapRow);
        } catch (DataAccessException e) {
            log.error("SQLRoomDAO findAll", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.all"));
        }
    }

    @Override
    public Room save(Room room) {
        log.debug("Accessing the database using the create command. Room = {}", room);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_ROOM_CREATE,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, room.getNumber());
                statement.setString(2, room.getType().toString().toUpperCase());
                statement.setString(3, room.getCapacity().toString().toUpperCase());
                statement.setBigDecimal(4, room.getPrice());
                statement.setString(5, room.getStatus().toString().toUpperCase());
                return statement;
            }, keyHolder);
            Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return findById(id);
        } catch (DataAccessException | NullPointerException e) {
            log.error("SQLRoomDAO create error new room = {}", room, e);
            throw new DaoException(MessageManger.getMessage("msg.error.create") + room);
        }
    }

    @Override
    public Room update(Room room) {
        log.debug("Accessing the database using the update command. Room = {}", room);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("roomNumber", room.getNumber());
            params.put("roomType", room.getType().toString().toUpperCase());
            params.put("roomCapacity", room.getCapacity().toString().toUpperCase());
            params.put("price", room.getPrice());
            params.put("status", room.getStatus().toString().toUpperCase());
            params.put("roomId", room.getId());
            namedParameterJdbcTemplate.update(SqlManager.SQL_ROOM_UPDATE, params);
            return findById(room.getId());
        } catch (DataAccessException e) {
            log.error("Command update can't be executed");
            throw new DaoException(MessageManger.getMessage("msg.error.command") + room);
        }

    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing the database using the delete command. Room id = {}", id);
        try {
            return jdbcTemplate.update(SqlManager.SQL_ROOM_DELETE, id) == 1;
        } catch (DataAccessException e) {
            log.error("SQLRoomDAO delete error id  = {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<Room> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_ROOM_PAGE, this::mapRow, limit, offset);
        } catch (DataAccessException e) {
            log.error("SQLRoomDAO findAllPages error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        }
    }

    @Override
    public Long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        try {
            return jdbcTemplate.queryForObject(SqlManager.SQL_ROOM_COUNT_ROOMS, Long.class);
        } catch (DataAccessException e) {
            log.error("SQLRoomDAO findRowCount error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.count"));
        }
    }

    public Room findRoomByNumber(String number) {
        log.debug("Accessing the database using the findRoomByNumber command. Room number = {}", number);
        try {
            return jdbcTemplate.queryForObject(SqlManager.SQL_ROOM_FIND_BY_NUMBER, this::mapRow, number);
        } catch (DataAccessException e) {
            log.error("SQLRoomDAO findRoomByNumber error number = {}", number, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find") + number);
        }
    }

    @Override
    public List<Room> findAvailableRooms(LocalDate check_in, LocalDate check_out, String type, String capacity) {
        log.debug("Accessing the database using the findAvailableRooms command");
        try {
            return jdbcTemplate.query(SqlManager.SQL_ROOM_FIND_AVAILABLE_ROOMS, this::mapRow, type.toUpperCase(),
                    capacity.toUpperCase(), java.sql.Date.valueOf(check_in), java.sql.Date.valueOf(check_out),
                    java.sql.Date.valueOf(check_in), java.sql.Date.valueOf(check_out));
        } catch (DataAccessException e) {
            log.error("SQLRoomDAO findAllAvailableRooms error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        }
    }


    /**
     * Method fills the Room object with data from the database
     *
     * @param result resulting query
     * @return Room object
     */
    private Room mapRow(ResultSet result, int num) throws SQLException {
        Room room = new Room();
        room.setId(result.getLong("id"));
        room.setNumber(result.getString("room_number"));
        room.setType(Room.RoomType.valueOf(result.getString("type")));
        room.setCapacity(Room.Capacity.valueOf(result.getString("capacity")));
        room.setPrice(result.getBigDecimal("price"));
        room.setStatus(Room.RoomStatus.valueOf(result.getString("status")));
        return room;
    }
}