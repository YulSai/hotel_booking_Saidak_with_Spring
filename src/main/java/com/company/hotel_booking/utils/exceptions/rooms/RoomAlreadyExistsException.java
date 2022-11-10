package com.company.hotel_booking.utils.exceptions.rooms;

/**
 * Class of errors coming from the service layer when creating or updating an object
 */
public class RoomAlreadyExistsException extends RoomServiceException {
    public RoomAlreadyExistsException(String message) {
        super(message);
    }
}