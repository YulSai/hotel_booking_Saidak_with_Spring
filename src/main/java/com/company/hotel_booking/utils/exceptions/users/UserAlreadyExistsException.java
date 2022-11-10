package com.company.hotel_booking.utils.exceptions.users;

/**
 * Class of errors coming from the service layer when creating or updating an object
 */
public class UserAlreadyExistsException extends UserServiceException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}