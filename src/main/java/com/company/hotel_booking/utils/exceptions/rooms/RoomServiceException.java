package com.company.hotel_booking.utils.exceptions.rooms;

import com.company.hotel_booking.utils.exceptions.ServiceException;

/**
 * Class of errors coming from the service layer
 */
public class RoomServiceException extends ServiceException {
    public RoomServiceException(String message) {
        super(message);
    }
}