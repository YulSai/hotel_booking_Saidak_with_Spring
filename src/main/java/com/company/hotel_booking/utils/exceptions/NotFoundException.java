package com.company.hotel_booking.utils.exceptions;

/**
 * Class of errors coming from the service layer
 */
public class NotFoundException extends AppException {
    public NotFoundException(String message) {
        super(message);
    }
}