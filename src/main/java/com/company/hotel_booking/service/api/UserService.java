package com.company.hotel_booking.service.api;

import com.company.hotel_booking.service.dto.UserDto;

/**
 * Interface for serving User objects according to the business logics of User
 */
public interface UserService extends AbstractService<Long, UserDto> {
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