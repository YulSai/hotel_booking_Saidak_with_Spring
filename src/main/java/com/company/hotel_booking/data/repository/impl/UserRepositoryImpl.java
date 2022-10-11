package com.company.hotel_booking.data.repository.impl;

import com.company.hotel_booking.data.repository.api.UserRepository;
import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.exceptions.RegistrationException;
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
 * Class object UserDAO with implementation of CRUD operation operations
 */
@Log4j2
@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findById(Long id) {
        log.debug("Accessing the database using the findById command. User's id = {}", id);
        try {
            return entityManager.find(User.class, id);
        } catch (HibernateException e) {
            log.error("SQLUserDAO findById error {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.by.id") + id);
        }
    }

    @Override
    public List<User> findAll() {
        log.debug("Accessing the database using the findAll command");
        try {
            return entityManager.createQuery(SqlManager.SQL_USER_FIND_ALL, User.class).getResultList();
        } catch (HibernateException e) {
            log.error("SQLUserDAO findAll", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.all"));
        }
    }

    @Override
    public User create(User user) {
        log.debug("Accessing the database using the create command. User = {}", user);
        try {
            entityManager.persist(user);
            return user;
        } catch (HibernateException | NullPointerException e) {
            log.error("SQLUserDAO create error ", e);
            throw new DaoException(MessageManager.getMessage("msg.error.create") + user);
        }
    }

    @Override
    public User update(User user) {
        log.debug("Accessing the database using the update command. User = {}", user);
        try {
            entityManager.merge(user);
            return user;
        } catch (HibernateException e) {
            log.error("SQLUserDAO update error. Failed to update user {}", user);
            throw new DaoException(MessageManager.getMessage("msg.error.update") + user);
        }
    }

    @Override
    public int delete(Long id) {
        log.debug("Accessing the database using the delete command. User's id = {}", id);
        try {
            return entityManager.createQuery(SqlManager.SQL_USER_DELETE).setParameter("id", id).executeUpdate();
        } catch (HibernateException e) {
            log.error("SQLUserDAO delete error User's id = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<User> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        try {
            return entityManager.createQuery(SqlManager.SQL_USER_PAGE, User.class)
                    .setMaxResults(limit)
                    .setFirstResult((int) offset)
                    .getResultList();
        } catch (HibernateException e) {
            log.error("SQLUserDAO findAllPages error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    public Long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        try {
            return (Long) entityManager.createQuery(SqlManager.SQL_USER_COUNT_USERS).getSingleResult();
        } catch (HibernateException e) {
            log.error("SQLUserDAO findRowCount error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.count"));
        }
    }

    @Override
    public User findUserByEmail(String email) {
        log.debug("Accessing the database using the findUserByEmail command. User's email = {}", email);
        try {
           return entityManager.createQuery(SqlManager.SQL_USER_FIND_BY_EMAIL, User.class)
                   .setParameter("email", email)
                   .getResultList()
                   .stream()
                   .findFirst().orElse(null);
        } catch (HibernateException e) {
            log.error("SQLUserDAO findUserByEmail error {}", email, e);
            throw new RegistrationException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    public int block(Long id) {
        log.debug("Accessing the database using the update command. User's id = {}", id);
        try {
           return entityManager.createQuery(SqlManager.SQL_USER_BLOCK).setParameter("id", id).executeUpdate();
        } catch (HibernateException e) {
            log.error("SQLUserDAO update error. Failed to update user {}", id);
            throw new DaoException(MessageManager.getMessage("msg.error.update") + id);
        }
    }
}