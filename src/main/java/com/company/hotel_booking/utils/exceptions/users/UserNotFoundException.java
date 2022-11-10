package com.company.hotel_booking.utils.exceptions.users;

import com.company.hotel_booking.utils.exceptions.NotFoundException;

/**
 * Class of errors coming from the service layer when finding for an object by id
 */
public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}