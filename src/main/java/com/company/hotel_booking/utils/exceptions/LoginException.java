package com.company.hotel_booking.utils.exceptions;

import org.springframework.stereotype.Component;

/**
 * Exception handling class on login
 */
@Component
public class LoginException extends RuntimeException {
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}