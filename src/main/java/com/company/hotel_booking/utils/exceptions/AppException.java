package com.company.hotel_booking.utils.exceptions;

/**
 * Class of errors
 */
public class AppException extends RuntimeException{
    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
