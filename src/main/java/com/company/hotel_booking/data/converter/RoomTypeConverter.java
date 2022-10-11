package com.company.hotel_booking.data.converter;

import com.company.hotel_booking.data.entity.Room;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoomTypeConverter implements AttributeConverter<Room.RoomType, Long> {
    @Override
    public Long convertToDatabaseColumn(Room.RoomType type) {
        if (type == null) {
            return null;
        }
        return type.getId();
    }

    @Override
    public Room.RoomType convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }
        return Stream.of(Room.RoomType.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
