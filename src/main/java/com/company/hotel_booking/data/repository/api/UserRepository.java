package com.company.hotel_booking.data.repository.api;

import com.company.hotel_booking.data.entity.User;


/**
 * Interface extends IAbstractDao interface for managing User entities
 */
public interface UserRepository extends AbstractRepository<Long, User> {

    /**
     * Method finds user in the data source by email
     *
     * @param email for search
     * @return user
     */
    Object findUserByEmail(String email);

    /**
     * Method is used for saving updating Entity objects with status Block in the data source
     *
     * @param id user id
     * @return updated entity object
     */
    int block(Long id);
}