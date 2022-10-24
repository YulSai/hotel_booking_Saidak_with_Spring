package com.company.hotel_booking.utils.exceptions;

import org.springframework.stereotype.Component;

/**
 * Exception handling class on login
 */
@Component
public class LoginUserException extends RuntimeException {
    public LoginUserException() {
    }
}