package com.company.hotel_booking.data.repository.impl;

import com.company.hotel_booking.aspects.logging.annotations.DaoEx;
import com.company.hotel_booking.aspects.logging.annotations.LogInvocationRepository;
import com.company.hotel_booking.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.data.repository.api.ReservationInfoRepository;
import com.company.hotel_booking.data.entity.ReservationInfo;
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
 * Class object ReservationInfoDao with implementation of CRUD operation operations
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class ReservationInfoRepositoryImpl implements ReservationInfoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @LogInvocationServer
    @DaoEx
    public ReservationInfo findById(Long id) {
        try {
            return entityManager.find(ReservationInfo.class, id);
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find.by.id") + id);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<ReservationInfo> findAll() {
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_INFO_FIND_ALL,
                    ReservationInfo.class).getResultList();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find.all"));
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public ReservationInfo create(ReservationInfo reservationInfo) {
        try {
            entityManager.persist(reservationInfo);
            return reservationInfo;
        } catch (HibernateException | NullPointerException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.create") + reservationInfo);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public ReservationInfo update(ReservationInfo reservationInfo) {
        try {
            entityManager.merge(reservationInfo);
            return reservationInfo;
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.command") + reservationInfo);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public int delete(Long id) {
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_INFO_DELETE).setParameter("id",
                    id).executeUpdate();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<ReservationInfo> findAllPages(int limit, long offset) {
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_INFO_PAGE, ReservationInfo.class)
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
            return (Long) entityManager.createQuery(
                    SqlManager.SQL_RESERVATION_INFO_COUNT_RESERVATIONS_INFO).getSingleResult();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find.count"));
        }
    }
}