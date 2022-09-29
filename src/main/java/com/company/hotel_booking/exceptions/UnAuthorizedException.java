package com.company.hotel_booking.exceptions;

/**
 * Exception handling class on authorization
 */
public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}