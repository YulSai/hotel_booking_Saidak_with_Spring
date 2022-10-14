package com.company.hotel_booking.data.repository.impl;

import com.company.hotel_booking.aspects.logging.DaoEx;
import com.company.hotel_booking.aspects.logging.LogInvocationRepository;
import com.company.hotel_booking.aspects.logging.RegistrationEx;
import com.company.hotel_booking.data.repository.api.UserRepository;
import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.exceptions.RegistrationException;
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
 * Class object UserDAO with implementation of CRUD operation operations
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @LogInvocationRepository
    @DaoEx
    public User findById(Long id) {
        try {
            return entityManager.find(User.class, id);
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find.by.id") + id);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<User> findAll() {
        try {
            return entityManager.createQuery(SqlManager.SQL_USER_FIND_ALL, User.class).getResultList();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find.all"));
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public User create(User user) {
        try {
            entityManager.persist(user);
            return user;
        } catch (HibernateException | NullPointerException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.create") + user);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public User update(User user) {
        try {
            entityManager.merge(user);
            return user;
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.update") + user);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public int delete(Long id) {
        try {
            return entityManager.createQuery(SqlManager.SQL_USER_DELETE).setParameter("id", id).executeUpdate();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public List<User> findAllPages(int limit, long offset) {
        try {
            return entityManager.createQuery(SqlManager.SQL_USER_PAGE, User.class)
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
            return (Long) entityManager.createQuery(SqlManager.SQL_USER_COUNT_USERS).getSingleResult();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.find.count"));
        }
    }

    @Override
    @LogInvocationRepository
    @RegistrationEx
    public User findUserByEmail(String email) {
        try {
            return entityManager.createQuery(SqlManager.SQL_USER_FIND_BY_EMAIL, User.class)
                    .setParameter("email", email)
                    .getResultList()
                    .stream()
                    .findFirst().orElse(null);
        } catch (HibernateException e) {
            throw new RegistrationException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    @LogInvocationRepository
    @DaoEx
    public int block(Long id) {
        try {
            return entityManager.createQuery(SqlManager.SQL_USER_BLOCK).setParameter("id", id).executeUpdate();
        } catch (HibernateException e) {
            throw new DaoException(MessageManager.getMessage("msg.error.update") + id);
        }
    }
}