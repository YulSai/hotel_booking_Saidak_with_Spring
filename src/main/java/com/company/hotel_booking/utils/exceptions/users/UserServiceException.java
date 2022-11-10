package com.company.hotel_booking.utils.exceptions.users;

import com.company.hotel_booking.utils.exceptions.ServiceException;

/**
 * Class of errors coming from the service layer
 */
public class UserServiceException extends ServiceException {
    public UserServiceException(String message) {
        super(message);
    }
}