package com.company.hotel_booking.service.api;

import com.company.hotel_booking.service.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for serving User objects according to the business logics of User
 */
public interface UserService extends AbstractService<Long, UserDto> {
    /**
     * Method finds a user in the database by username
     *
     * @param username    user username
     * @return User if found
     */
    UserDto findByUsername(String username);

    /**
     * Method replaces the old password with the new one entered by the user
     *
     * @param userDto user
     * @return User with new password
     */
    UserDto changePassword(UserDto userDto);

    /**
     * Method sets to user avatar and saves UserDto object
     * @param userDto object UserDto
     * @param avatarFile MultipartFile
     * @return object UserDto
     */
    UserDto processCreateUser(UserDto userDto, MultipartFile avatarFile);

    /**
     * Method sets to user avatar and updates UserDto object
     * @param userDto object UserDto
     * @param avatarFile MultipartFile
     * @return object UserDto
     */
    UserDto processUserUpdates(UserDto userDto, MultipartFile avatarFile);

}