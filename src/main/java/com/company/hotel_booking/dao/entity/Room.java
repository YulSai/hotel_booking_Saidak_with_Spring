package com.company.hotel_booking.dao.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Class describing the object Room
 */
@Data
public class Room {
    private Long id;
    private String number;
    private RoomType type;
    private Capacity capacity;
    private BigDecimal price;
    private RoomStatus status;

    public enum RoomStatus {
        AVAILABLE,
        UNAVAILABLE
    }

    public enum RoomType {
        STANDARD,
        COMFORT,
        LUX,
        PRESIDENT
    }

    public enum Capacity {
        SINGLE,
        DOUBLE,
        TRIPLE,
        FAMILY
    }

    public Room() {
    }

    public Room(Long id, String number, RoomType type, Capacity capacity, BigDecimal price, RoomStatus status) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
    }
}