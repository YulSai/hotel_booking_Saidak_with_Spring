package com.company.hotel_booking.dao.api;

import com.company.hotel_booking.dao.entity.User;


/**
 * Interface extends IAbstractDao interface for managing User entities
 */
public interface IUserDao extends IAbstractDao<Long, User> {

    /**
     * Method finds user in the data source by email
     *
     * @param email for search
     * @return user
     */
    User findUserByEmail(String email);
}