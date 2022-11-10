package com.company.hotel_booking.utils.exceptions.reservationsInfo;

import com.company.hotel_booking.utils.exceptions.NotFoundException;

/**
 * Class of errors coming from the service layer when finding for an object by id
 */
public class ReservationInfoNotFoundException extends NotFoundException {
    public ReservationInfoNotFoundException(String message) {
        super(message);
    }
}