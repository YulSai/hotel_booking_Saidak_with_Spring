package com.company.hotel_booking.utils.exceptions.rooms;

import com.company.hotel_booking.utils.exceptions.NotFoundException;

public class RoomNotFoundException extends NotFoundException {
    public RoomNotFoundException(String message) {
        super(message);
    }
}