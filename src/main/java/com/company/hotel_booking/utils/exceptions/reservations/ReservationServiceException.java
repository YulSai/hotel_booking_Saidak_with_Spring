package com.company.hotel_booking.utils.exceptions.reservations;

import com.company.hotel_booking.utils.exceptions.ServiceException;

/**
 * Class of errors coming from the service layer
 */
public class ReservationServiceException extends ServiceException {
    public ReservationServiceException(String message) {
        super(message);
    }
}