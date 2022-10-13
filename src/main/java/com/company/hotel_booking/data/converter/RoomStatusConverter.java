package com.company.hotel_booking.data.converter;

import com.company.hotel_booking.data.entity.Room;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * Class for mapping an enum Status of room value to its database representation
 */
@Converter(autoApply = true)
public class RoomStatusConverter implements AttributeConverter<Room.RoomStatus, Long> {
    @Override
    public Long convertToDatabaseColumn(Room.RoomStatus status) {
        if (status == null) {
            return null;
        }
        return status.getId();
    }

    @Override
    public Room.RoomStatus convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }
        return Stream.of(Room.RoomStatus.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
