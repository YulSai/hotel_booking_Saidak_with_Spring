package com.company.hotel_booking.utils.exceptions.reservationsInfo;

/**
 * Class of errors coming from the service layer when deleting an object
 */
public class ReservationInfoDeleteException extends ReservationInfoServiceException {
    public ReservationInfoDeleteException(String message) {
        super(message);
    }
}