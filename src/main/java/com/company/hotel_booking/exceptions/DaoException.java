package com.company.hotel_booking.exceptions;

public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }
}