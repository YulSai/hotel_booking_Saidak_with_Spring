package com.company.hotel_booking.utils.exceptions.reservationsInfo;

import com.company.hotel_booking.utils.exceptions.ServiceException;

/**
 * Class of errors coming from the service layer
 */
public class ReservationInfoServiceException extends ServiceException {
    public ReservationInfoServiceException(String message) {
        super(message);
    }
}