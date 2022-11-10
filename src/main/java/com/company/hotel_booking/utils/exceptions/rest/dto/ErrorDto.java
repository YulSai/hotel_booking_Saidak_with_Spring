package com.company.hotel_booking.utils.exceptions.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class describing the object ErrorDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private String message;
}
