package com.company.hotel_booking.utils.exceptions.users;

public class UserNotFoundException extends UserServiceException{
    public UserNotFoundException(String message) {
        super(message);
    }
}