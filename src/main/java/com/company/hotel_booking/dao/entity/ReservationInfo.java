package com.company.hotel_booking.dao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class describing the object ReservationInfo
 */
@Data
public class ReservationInfo {
    private Long id;
    private Long reservationId;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long nights;
    private BigDecimal roomPrice;
}