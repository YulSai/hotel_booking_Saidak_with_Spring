package com.company.hotel_booking.utils.exceptions.reservationsInfo;

public class ReservationInfoNotFoundException extends ReservationInfoServiceException {
    public ReservationInfoNotFoundException(String message) {
        super(message);
    }
}