package com.company.hotel_booking.utils.exceptions.rest;

import com.company.hotel_booking.utils.exceptions.users.ClientException;
import lombok.Getter;
import org.springframework.validation.Errors;

/**
 * Class of errors coming from the rest controller when validating an object
 */
public class ValidationException extends ClientException {
    @Getter
    private final Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }
}
