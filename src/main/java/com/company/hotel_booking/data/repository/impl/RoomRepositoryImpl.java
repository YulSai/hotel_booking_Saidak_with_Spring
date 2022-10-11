package com.company.hotel_booking.data.repository.impl;

import com.company.hotel_booking.data.repository.api.RoomRepository;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.SqlManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Class object RoomDAO with implementation of CRUD operation operations
 */
@Log4j2
@Repository
@Transactional
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Room findById(Long id) {
        log.debug("Accessing the database using the findById  command. Room id = {}", id);
        try {
            return entityManager.find(Room.class, id);
        } catch (HibernateException e) {
            log.error("SQLRoomDAO findById error id = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.by.id") + id);
        }
    }

    public List<Room> findAll() {
        log.debug("Accessing the database using the findAll command");
        try {
            return entityManager.createQuery(SqlManager.SQL_ROOM_FIND_ALL, Room.class).getResultList();
        } catch (HibernateException e) {
            log.error("SQLRoomDAO findAll", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.all"));
        }
    }

    @Override
    public Room create(Room room) {
        log.debug("Accessing the database using the create command. Room = {}", room);
        try {
            entityManager.persist(room);
            return room;
        } catch (HibernateException | NullPointerException e) {
            log.error("SQLRoomDAO create error new room = {}", room, e);
            throw new DaoException(MessageManager.getMessage("msg.error.create") + room);
        }
    }

    @Override
    public Room update(Room room) {
        log.debug("Accessing the database using the update command. Room = {}", room);
        try {
            entityManager.merge(room);
            return room;
        } catch (HibernateException e) {
            log.error("Command update can't be executed");
            throw new DaoException(MessageManager.getMessage("msg.error.command") + room);
        }
    }

    @Override
    public int delete(Long id) {
        log.debug("Accessing the database using the delete command. Room id = {}", id);
        try {
            return entityManager.createQuery(SqlManager.SQL_ROOM_DELETE).setParameter("id", id).executeUpdate();
        } catch (HibernateException e) {
            log.error("SQLRoomDAO delete error id  = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<Room> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        try {
            return entityManager.createQuery(SqlManager.SQL_ROOM_PAGE, Room.class)
                    .setMaxResults(limit)
                    .setFirstResult((int) offset)
                    .getResultList();
        } catch (HibernateException e) {
            log.error("SQLRoomDAO findAllPages error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    public Long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        try {
            return (Long) entityManager.createQuery(SqlManager.SQL_ROOM_COUNT_ROOMS).getSingleResult();
        } catch (HibernateException e) {
            log.error("SQLRoomDAO findRowCount error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.count"));
        }
    }

    public Room findRoomByNumber(String number) {
        log.debug("Accessing the database using the findRoomByNumber command. Room number = {}", number);
        try {
            return entityManager.createQuery(SqlManager.SQL_ROOM_FIND_BY_NUMBER, Room.class)
                    .setParameter("number", number)
                    .getResultList()
                    .stream()
                    .findFirst().orElse(null);
        } catch (HibernateException e) {
            log.error("SQLRoomDAO findRoomByNumber error number = {}", number, e);
            throw new DaoException(MessageManager.getMessage("msg.error.find") + number);
        }
    }

    @Override
    public List<Room> findAvailableRooms(LocalDate check_in, LocalDate check_out, String type, String capacity) {
        log.debug("Accessing the database using the findAvailableRooms command");
        try {
            Long tId = Room.RoomType.valueOf(type.toUpperCase()).getId();
            Long cId = Room.Capacity.valueOf(capacity.toUpperCase()).getId();
            return entityManager.createNativeQuery(SqlManager.SQL_ROOM_FIND_AVAILABLE_ROOMS, Room.class)
                    .setParameter(1, tId)
                    .setParameter(2, cId)
                    .setParameter(3, Date.valueOf(check_in))
                    .setParameter(4, Date.valueOf(check_out))
                    .setParameter(5, Date.valueOf(check_in))
                    .setParameter(6, Date.valueOf(check_out))
                    .getResultList();
        } catch (HibernateException e) {
            log.error("SQLRoomDAO findAllAvailableRooms error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }
}