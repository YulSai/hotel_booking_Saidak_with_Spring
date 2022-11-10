package com.company.hotel_booking.utils.exceptions.rooms;

/**
 * Class of errors coming from the service layer when deleting an object
 */
public class RoomDeleteException extends RoomServiceException {
    public RoomDeleteException(String message) {
        super(message);
    }
}