package com.company.hotel_booking.data.converter;

import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * Class for mapping an enum Type of room value to its database representation
 */
@Converter(autoApply = true)
public class RoomTypeConverter implements AttributeConverter<Room.RoomType, Long> {
    @Override
    @LogInvocation
    public Long convertToDatabaseColumn(Room.RoomType type) {
        if (type == null) {
            return null;
        }
        return type.getId();
    }

    @Override
    @LogInvocation
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
