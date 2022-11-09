package com.company.hotel_booking.utils.exceptions.reservations;

import com.company.hotel_booking.utils.exceptions.NotFoundException;

public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}