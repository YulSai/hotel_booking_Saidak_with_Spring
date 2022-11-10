package com.company.hotel_booking.utils.exceptions;

/**
 * Class of errors coming from the service layer
 */
public class ServiceException extends AppException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}