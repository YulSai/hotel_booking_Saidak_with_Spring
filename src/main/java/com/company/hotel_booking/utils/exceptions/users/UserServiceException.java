package com.company.hotel_booking.utils.exceptions.users;

import com.company.hotel_booking.utils.exceptions.ServiceException;

public class UserServiceException extends ServiceException {
    public UserServiceException(String message) {
        super(message);
    }
}