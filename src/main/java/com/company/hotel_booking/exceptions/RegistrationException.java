package com.company.hotel_booking.exceptions;


public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super(message);
    }
}