package com.company.hotel_booking.utils.exceptions.users;

import com.company.hotel_booking.utils.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}