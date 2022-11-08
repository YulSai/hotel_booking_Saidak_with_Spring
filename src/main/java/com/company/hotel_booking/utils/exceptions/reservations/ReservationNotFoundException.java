package com.company.hotel_booking.utils.exceptions.reservations;

public class ReservationNotFoundException extends ReservationServiceException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}