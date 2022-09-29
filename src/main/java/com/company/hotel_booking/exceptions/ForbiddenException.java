package com.company.hotel_booking.exceptions;

/**
 * Exception handling class for a forbidden request
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}