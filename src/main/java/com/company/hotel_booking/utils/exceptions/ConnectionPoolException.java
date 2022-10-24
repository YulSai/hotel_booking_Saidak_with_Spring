package com.company.hotel_booking.utils.exceptions;


import java.io.IOException;

public class ConnectionPoolException extends RuntimeException {
    public ConnectionPoolException(String message, IOException e) {
    }
}
