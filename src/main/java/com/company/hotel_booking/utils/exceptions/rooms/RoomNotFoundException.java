package com.company.hotel_booking.utils.exceptions.rooms;

import com.company.hotel_booking.utils.exceptions.NotFoundException;

/**
 * Class of errors coming from the service layer when finding for an object by id
 */
public class RoomNotFoundException extends NotFoundException {
    public RoomNotFoundException(String message) {
        super(message);
    }
}