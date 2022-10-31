package com.company.hotel_booking.utils.exceptions.reservations;

import com.company.hotel_booking.utils.exceptions.ServiceException;

public class ReservationServiceException extends ServiceException {
    public ReservationServiceException() {
    }

    public ReservationServiceException(String message) {
        super(message);
    }

    public ReservationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}