package com.company.hotel_booking.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

/**
 * Class describing the object ReservationInfoDto
 */
@Getter
@Setter
@RequiredArgsConstructor
public class ReservationInfoDto {
    private Long id;
    private ReservationDto reservationDto;
    private RoomDto room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long nights;
    private BigDecimal roomPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReservationInfoDto reservationInfo = (ReservationInfoDto) o;
        return id != null && Objects.equals(id, reservationInfo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ReservationInfoDto{" +
                "id=" + id +
                ", reservation=" + reservationDto+
                ", room=" + room +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", nights=" + nights +
                ", roomPrice=" + roomPrice +
                '}';
    }
}