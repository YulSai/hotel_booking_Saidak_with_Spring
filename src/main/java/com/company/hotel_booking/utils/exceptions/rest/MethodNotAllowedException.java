package com.company.hotel_booking.utils.exceptions.rest;

import com.company.hotel_booking.utils.exceptions.AppException;

public class MethodNotAllowedException extends AppException {
    public MethodNotAllowedException(String message) {
        super(message);
    }
}
