package com.company.hotel_booking.utils.exceptions.users;

/**
 * Class of errors coming from the service layer when deleting an object
 */
public class UserDeleteException extends UserServiceException{
    public UserDeleteException(String message) {
        super(message);
    }
}