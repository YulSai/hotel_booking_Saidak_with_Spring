package com.company.hotel_booking.utils.exceptions.reservationsInfo;

import com.company.hotel_booking.utils.exceptions.NotFoundException;

public class ReservationInfoNotFoundException extends NotFoundException {
    public ReservationInfoNotFoundException(String message) {
        super(message);
    }
}