package com.company.hotel_booking.data.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class describing the object Reservation
 */
@Data
public class Reservation {
    private Long id;
    private User user;
    private BigDecimal totalCost;
    private Status status;
    List<ReservationInfo> details;

    public enum Status {
        IN_PROGRESS,
        CONFIRMED,
        REJECTED,
        DELETED
    }
}