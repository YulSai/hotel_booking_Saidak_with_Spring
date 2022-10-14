package com.company.hotel_booking.data.repository.impl;

import com.company.hotel_booking.aspects.logging.DaoEx;
import com.company.hotel_booking.aspects.logging.LogInvocationRepository;
import com.company.hotel_booking.data.repository.api.ReservationRepository;
import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.SqlManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Class object ReservationDao with implementation of CRUD operation operations
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @LogInvocationRepository
    @DaoEx
    public Reservation findById(Long id) {
        try {
            return entityManager.find(Reservation.class, id);
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find.by.id") + id);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<Reservation> findAll() {
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_FIND_ALL, Reservation.class).getResultList();
                } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find.all"));
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public Reservation create(Reservation reservation) {
        try {
            entityManager.persist(reservation);
            return reservation;
        } catch (HibernateException | NullPointerException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.create") + reservation);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public Reservation update(Reservation reservation) {
        try {
            entityManager.merge(reservation);
            return reservation;
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.update") + reservation);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public int delete(Long id) {
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_DELETE).setParameter("id", id).executeUpdate();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<Reservation> findAllPages(int limit, long offset) {
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_PAGE, Reservation.class)
                    .setMaxResults(limit)
                    .setFirstResult((int) offset)
                    .getResultList();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<Reservation> findAllPagesByUsers(int limit, long offset, Long id) {
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_PAGE_BY_USER, Reservation.class)
                    .setParameter("userId", id)
                    .setMaxResults(limit)
                    .setFirstResult((int) offset)
                    .getResultList();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public Long countRow() throws DaoException {
        try {
            return (Long) entityManager.createQuery(SqlManager.SQL_RESERVATION_COUNT_RESERVATIONS).getSingleResult();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find.count"));
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<Reservation> findAllByUsers(Long id) {
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_BY_USER, Reservation.class)
                    .setParameter("userId", id)
                    .getResultList();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }
}