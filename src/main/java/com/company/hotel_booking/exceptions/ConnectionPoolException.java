package com.company.hotel_booking.exceptions;

import java.sql.SQLException;

public class ConnectionPoolException extends RuntimeException {
    public ConnectionPoolException(String s, SQLException e) {
    }
    public ConnectionPoolException(String s, Exception e) {
    }
}
