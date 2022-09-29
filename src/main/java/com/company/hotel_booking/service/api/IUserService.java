package com.company.hotel_booking.service.api;

import com.company.hotel_booking.service.dto.UserDto;

import java.util.List;

/**
 * Interface for serving User objects according to the business logics of User
 */
public interface IUserService extends IAbstractService<Long, UserDto> {
    /**
     * Method gets all user Dto
     *
     * @return list of user Dto
     */
    List<UserDto> findAll();

    /**
     * Method is used for user authentication
     *
     * @param email    user email
     * @param password user password
     * @return User if found
     */
    UserDto login(String email, String password);

    /**
     * Method replaces the old password with the new one entered by the user
     *
     * @param userDto user
     * @return User with new password
     */
    UserDto changePassword(UserDto userDto);
}