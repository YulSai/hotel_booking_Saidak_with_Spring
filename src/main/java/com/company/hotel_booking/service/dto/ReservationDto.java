package com.company.hotel_booking.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class describing the object ReservationDto
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ReservationDto {
    private Long id;
    private UserDto user;
    private BigDecimal totalCost;
    private StatusDto status;
    @ToString.Exclude
    private List<ReservationInfoDto> details = new ArrayList<>();

    public void addDetails (ReservationInfoDto info){
        details.add(info);
        info.setReservation(this);
    }

    public enum StatusDto {
        IN_PROGRESS,
        CONFIRMED,
        REJECTED,
        DELETED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReservationDto reservation = (ReservationDto) o;
        return id != null && Objects.equals(id, reservation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}