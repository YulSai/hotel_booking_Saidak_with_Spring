package com.company.hotel_booking.service.dto;

import com.company.hotel_booking.utils.managers.ValidationManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @NotNull(message = "{msg.create.new.room.type.empty}")
    private RoomTypeDto type;

    @NotNull(message = "{msg.create.new.room.capacity.empty}")
    private RoomStatusDto status;

    @NotNull(message = "{msg.create.new.room.status.empt}")
    private CapacityDto capacity;

    @NotNull(message = "{msg.create.new.room.price.empty}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{msg.create.new.room.price.min}")
    @DecimalMax(value = "10000.00", inclusive = false, message = "{msg.create.new.room.price.max}" )
    @Digits(integer=4, fraction=2, message = "{msg.create.new.room.price.format}")
    private BigDecimal price;

    @NotBlank(message = "{msg.create.new.room.number.empty}")
    @Pattern(regexp = ValidationManager.ROOM_NUMBER, message = "{msg.create.new.room.number.format}")
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