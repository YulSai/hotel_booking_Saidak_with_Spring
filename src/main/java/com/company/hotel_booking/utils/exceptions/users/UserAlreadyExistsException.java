package com.company.hotel_booking.utils.exceptions.users;

public class UserAlreadyExistsException extends UserServiceException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}