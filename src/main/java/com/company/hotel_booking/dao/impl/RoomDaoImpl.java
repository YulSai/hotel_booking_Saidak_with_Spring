package com.company.hotel_booking.dao.impl;

import com.company.hotel_booking.dao.api.IRoomDao;
import com.company.hotel_booking.dao.connection.DataSource;
import com.company.hotel_booking.dao.entity.Room;
import com.company.hotel_booking.exceptions.ConnectionPoolException;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.managers.SqlManager;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class object RoomDAO with implementation of CRUD operation operations
 */
@Log4j2
public class RoomDaoImpl implements IRoomDao {
    private final DataSource dataSource;

    public RoomDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Room findById(Long id) {
        log.debug("Accessing the database using the findById  command. Room id = {}", id);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_ROOM_FIND_BY_ID)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return processRoom(result);
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
    public Room findById(Long id, Connection connection) {
        log.debug("Accessing the database using the findById  command. Room id = {}", id);
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_ROOM_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return processRoom(result);
            }
        } catch (SQLException e) {
            log.error("SQLRoomDAO findById error id = {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.by.id") + id);
        }
        return null;
    }

    public List<Room> findAll() {
        log.debug("Accessing the database using the findAll command");
        List<Room> rooms = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(SqlManager.SQL_ROOM_FIND_ALL)) {
            connection.setAutoCommit(false);

            while (result.next()) {
                rooms.add(processRoom(result));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO findAll", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.all"));
        } finally {
            close(connection);
        }
        return rooms;
    }

    @Override
    public Room save(Room room) {
        log.debug("Accessing the database using the create command. Room = {}", room);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_ROOM_CREATE,
                Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            extractedDate(room, statement);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            connection.commit();

            if (keys.next()) {
                Long id = keys.getLong("id");
                return findById(id, connection);
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO create error new room = {}", room, e);
        } finally {
            close(connection);
        }
        throw new DaoException(MessageManger.getMessage("msg.error.create") + room);
    }

    @Override
    public Room update(Room room) {
        log.debug("Accessing the database using the update command. Room = {}", room);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_ROOM_UPDATE)) {
            connection.setAutoCommit(false);
            extractedDate(room, statement);
            statement.setLong(6, room.getId());

            if (statement.executeUpdate() == 0) {
                log.error("Command update can't be executed");
                throw new DaoException(MessageManger.getMessage("msg.error.command") + room);
            }
            connection.commit();
            return findById(room.getId(), connection);
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO update error. Failed to update room = {}", room, e);
            throw new DaoException(MessageManger.getMessage("msg.error.update") + room);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing the database using the delete command. Room id = {}", id);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_ROOM_DELETE)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);

            int rowsDeleted = statement.executeUpdate();
            connection.commit();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO delete error id  = {}", id, e);
            throw new DaoException(MessageManger.getMessage("msg.error.delete") + id);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<Room> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        List<Room> rooms = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_ROOM_PAGE)) {
            connection.setAutoCommit(false);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                rooms.add(processRoom(result));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO findAllPages error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        } finally {
            close(connection);
        }
        return rooms;
    }

    @Override
    public long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_ROOM_COUNT_ROOMS)) {
            connection.setAutoCommit(false);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO findRowCount error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find.count"));
        } finally {
            close(connection);
        }
        throw new DaoException(MessageManger.getMessage("msg.error.find.count"));
    }

    public Room findRoomByNumber(String number) {
        log.debug("Accessing the database using the findRoomByNumber command. Room number = {}", number);
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_ROOM_FIND_BY_NUMBER)) {
            connection.setAutoCommit(false);
            statement.setString(1, number);
            ResultSet result = statement.executeQuery();
            connection.commit();

            if (result.next()) {
                return processRoom(result);
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO findRoomByNumber error number = {}", number, e);
            throw new DaoException(MessageManger.getMessage("msg.error.find") + number);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public List<Room> findAvailableRooms(LocalDate check_in, LocalDate check_out, String type, String capacity) {
        log.debug("Accessing the database using the findAvailableRooms command");
        List<Room> rooms = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlManager.SQL_ROOM_FIND_AVAILABLE_ROOMS)) {
            connection.setAutoCommit(false);
            statement.setString(1, type.toUpperCase());
            statement.setString(2, capacity.toUpperCase());
            statement.setDate(3, java.sql.Date.valueOf(check_in));
            statement.setDate(4, java.sql.Date.valueOf(check_out));
            statement.setDate(5, java.sql.Date.valueOf(check_in));
            statement.setDate(6, java.sql.Date.valueOf(check_out));
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                rooms.add(processRoom(result));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQLRoomDAO findAllAvailableRooms error", e);
            throw new DaoException(MessageManger.getMessage("msg.error.find"));
        } finally {
            close(connection);
        }
        return rooms;
    }


    /**
     * Method fills the Room object with data from the database
     *
     * @param result resulting query
     * @return Room object
     */
    private Room processRoom(ResultSet result) throws SQLException {

        Room room = new Room();
        room.setId(result.getLong("id"));
        room.setNumber(result.getString("room_number"));
        room.setType(Room.RoomType.valueOf(result.getString("type")));
        room.setCapacity(Room.Capacity.valueOf(result.getString("capacity")));
        room.setPrice(result.getBigDecimal("price"));
        room.setStatus(Room.RoomStatus.valueOf(result.getString("status")));
        return room;
    }

    /**
     * Method extracts the object's fields data
     *
     * @param room      object Room
     * @param statement an object that represents a precompiled SQL statement
     */
    private void extractedDate(Room room, PreparedStatement statement) throws SQLException {
        statement.setString(1, room.getNumber());
        statement.setString(2, room.getType().toString().toUpperCase());
        statement.setString(3, room.getCapacity().toString().toUpperCase());
        statement.setBigDecimal(4, room.getPrice());
        statement.setString(5, room.getStatus().toString().toUpperCase());
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