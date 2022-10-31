package com.company.hotel_booking.utils.exceptions.rooms;

public class RoomAlreadyExistsException extends RoomServiceException {
    public RoomAlreadyExistsException(String message) {
        super(message);
    }
}