package com.company.hotel_booking.data.repository.impl;

import com.company.hotel_booking.data.repository.api.ReservationRepository;
import com.company.hotel_booking.data.entity.Reservation;
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
import java.util.List;

/**
 * Class object ReservationDao with implementation of CRUD operation operations
 */
@Log4j2
@Repository
@Transactional
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Reservation findById(Long id) {
        log.debug("Accessing the database using the findById command. Reservation id = {}", id);
        try {
            return entityManager.find(Reservation.class, id);
        } catch (HibernateException e) {
            log.error("SQLRoomDAO findById error id = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.by.id") + id);
        }
    }

    @Override
    public List<Reservation> findAll() {
        log.debug("Accessing the database using the findAll command");
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_FIND_ALL, Reservation.class).getResultList();
                } catch (HibernateException e) {
            log.error("SQLRoomDAO findAll", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.all"));
        }
    }

    @Override
    public Reservation create(Reservation reservation) {
        log.debug("Accessing the database using the save command");
        try {
            entityManager.persist(reservation);
            return reservation;
        } catch (HibernateException | NullPointerException e) {
            log.error("SQLReservationDAO save error: " + reservation, e);
            throw new DaoException(MessageManager.getMessage("msg.error.create") + reservation);
        }
    }

    @Override
    public Reservation update(Reservation reservation) {
        log.debug("Accessing the database using the update command reservation");
        try {
            entityManager.merge(reservation);
            return reservation;
        } catch (HibernateException e) {
            log.error("SQLRoomDAO update error. Failed to update reservation = {}", reservation, e);
            throw new DaoException(MessageManager.getMessage("msg.error.update") + reservation);
        }
    }

    @Override
    public int delete(Long id) {
        log.debug("Accessing the database using the delete command. Reservation id = {}", id);
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_DELETE).setParameter("id", id).executeUpdate();
        } catch (HibernateException e) {
            log.error("SQLReservationDAO delete error id  = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<Reservation> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_PAGE, Reservation.class)
                    .setMaxResults(limit)
                    .setFirstResult((int) offset)
                    .getResultList();
        } catch (HibernateException e) {
            log.error("SQLReservationDAO findAllPages error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    public List<Reservation> findAllPagesByUsers(int limit, long offset, Long id) {
        log.debug("Accessing the database using the findAllPagesByUsers command");
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_PAGE_BY_USER, Reservation.class)
                    .setParameter("userId", id)
                    .setMaxResults(limit)
                    .setFirstResult((int) offset)
                    .getResultList();
        } catch (HibernateException e) {
            log.error("SQLReservationDAO findAllPages error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    public Long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        try {
            return (Long) entityManager.createQuery(SqlManager.SQL_RESERVATION_COUNT_RESERVATIONS).getSingleResult();
        } catch (HibernateException e) {
            log.error("SQLReservationDAO findRowCount error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.count"));
        }
    }

    public List<Reservation> findAllByUsers(Long id) {
        log.debug("Accessing the database using the findAllByUsers command");
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_BY_USER, Reservation.class)
                    .setParameter("userId", id)
                    .getResultList();
        } catch (HibernateException e) {
            log.error("SQLReservationDAO findAllPages error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }
}