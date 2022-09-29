package com.company.hotel_booking.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class describing the object ReservationDto
 */
@Data
public class ReservationDto {
    private Long id;
    private UserDto user;
    private BigDecimal totalCost;
    private StatusDto status;
    List<ReservationInfoDto> details;

    public enum StatusDto {
        IN_PROGRESS,
        CONFIRMED,
        REJECTED,
        DELETED
    }
}