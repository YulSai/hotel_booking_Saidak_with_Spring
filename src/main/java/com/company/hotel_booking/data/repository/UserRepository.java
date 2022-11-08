package com.company.hotel_booking.data.repository;

import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.utils.constants.SqlConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


/**
 * Interface extends JpaRepository interface for managing User entities
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Method finds user in the data source by email
     *
     * @param email for search
     * @return user
     */
    Optional<User> findByEmail(String email);

    /**
     * Method is used for saving updating Entity objects with status Block in the data source
     *
     * @param id user id
     */
    @Modifying
    @Query(SqlConstants.SQL_USER_BLOCK)
    void block(Long id);

    /**
     * Method is used for saving updating Entity objects with status Block in the data source
     *
     * @param id user id
     */
    @Query(SqlConstants.SQL_USER_CHECK_BLOCK)
    Optional<User> checkBlock(Long id);
}