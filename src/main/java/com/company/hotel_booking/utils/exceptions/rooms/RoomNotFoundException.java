package com.company.hotel_booking.utils.exceptions.rooms;

public class RoomNotFoundException extends RoomServiceException {
    public RoomNotFoundException(String message) {
        super(message);
    }
}