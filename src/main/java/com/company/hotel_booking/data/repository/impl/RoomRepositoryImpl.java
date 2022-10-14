package com.company.hotel_booking.data.repository.impl;

import com.company.hotel_booking.aspects.logging.annotations.DaoEx;
import com.company.hotel_booking.aspects.logging.annotations.LogInvocationRepository;
import com.company.hotel_booking.data.repository.api.RoomRepository;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.SqlManager;
import lombok.RequiredArgsConstructor;
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
@Repository
@Transactional
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @LogInvocationRepository
    @DaoEx
    public Room findById(Long id) {
        try {
            return entityManager.find(Room.class, id);
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.room.error.find.by.id") + id);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<Room> findAll() {
        try {
            return entityManager.createQuery(SqlManager.SQL_ROOM_FIND_ALL, Room.class).getResultList();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.rooms.error.find.all"));
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public Room create(Room room) {
        try {
            entityManager.persist(room);
            return room;
        } catch (HibernateException | NullPointerException e) {
            throw new DaoException(MessageManager.getMessage("msg.room.error.create") + room);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public Room update(Room room) {
        try {
            entityManager.merge(room);
            return room;
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.room.error.update") + room);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public int delete(Long id) {
        try {
            return entityManager.createQuery(SqlManager.SQL_ROOM_DELETE).setParameter("id", id).executeUpdate();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.room.error.delete") + id);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<Room> findAllPages(int limit, long offset) {
        try {
            return entityManager.createQuery(SqlManager.SQL_ROOM_PAGE, Room.class)
                    .setMaxResults(limit)
                    .setFirstResult((int) offset)
                    .getResultList();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.rooms.error.find.all"));
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public Long countRow() throws DaoException {
        try {
            return (Long) entityManager.createQuery(SqlManager.SQL_ROOM_COUNT_ROOMS).getSingleResult();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.rooms.error.find.count"));
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public Room findRoomByNumber(String number) {
        try {
            return entityManager.createQuery(SqlManager.SQL_ROOM_FIND_BY_NUMBER, Room.class)
                    .setParameter("number", number)
                    .getResultList()
                    .stream()
                    .findFirst().orElse(null);
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.room.error.find.by.number") + number);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<Room> findAvailableRooms(LocalDate check_in, LocalDate check_out, String type, String capacity) {
        try {
            return entityManager.createNativeQuery(SqlManager.SQL_ROOM_FIND_AVAILABLE_ROOMS, Room.class)
                    .setParameter(1, Room.RoomType.valueOf(type.toUpperCase()).getId())
                    .setParameter(2, Room.Capacity.valueOf(capacity.toUpperCase()).getId())
                    .setParameter(3, Date.valueOf(check_in))
                    .setParameter(4, Date.valueOf(check_out))
                    .setParameter(5, Date.valueOf(check_in))
                    .setParameter(6, Date.valueOf(check_out))
                    .getResultList();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.rooms.error.find.available"));
        }
    }
}