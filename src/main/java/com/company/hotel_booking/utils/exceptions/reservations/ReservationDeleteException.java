package com.company.hotel_booking.utils.exceptions.reservations;

/**
 * Class of errors coming from the service layer when deleting an object
 */
public class ReservationDeleteException extends ReservationServiceException {
    public ReservationDeleteException(String message) {
        super(message);
    }
}