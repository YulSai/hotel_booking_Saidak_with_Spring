package com.company.hotel_booking.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class describing the object RoomDto
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RoomDto {
    private Long id;
    private RoomTypeDto type;
    private RoomStatusDto status;
    private CapacityDto capacity;
    private BigDecimal price;
    private String number;

    public enum RoomStatusDto {
        AVAILABLE,
        UNAVAILABLE
    }

    public enum RoomTypeDto {
        STANDARD,
        COMFORT,
        LUX,
        PRESIDENT
    }

    public enum CapacityDto {
        SINGLE,
        DOUBLE,
        TRIPLE,
        FAMILY
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoomDto room = (RoomDto) o;
        return id != null && Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}