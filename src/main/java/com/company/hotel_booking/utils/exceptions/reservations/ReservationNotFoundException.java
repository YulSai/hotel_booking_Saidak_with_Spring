package com.company.hotel_booking.utils.exceptions.reservations;

import com.company.hotel_booking.utils.exceptions.NotFoundException;

/**
 * Class of errors coming from the service layer when finding for an object by id
 */
public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}