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

    int delete2(Long id);
}