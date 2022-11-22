package com.company.hotel_booking.utils.exceptions;

/**
 * Class of errors coming from the service layer
 */
public class ServiceException extends AppException {
    public ServiceException(String message) {
        super(message);
    }
}