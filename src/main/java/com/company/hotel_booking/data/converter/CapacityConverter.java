package com.company.hotel_booking.data.converter;

import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * Class for mapping an enum Capacity of room value to its database representation
 */
@Converter(autoApply = true)
public class CapacityConverter implements AttributeConverter<Room.Capacity, Long> {
    @Override
    @LogInvocation
    public Long convertToDatabaseColumn(Room.Capacity capacity) {
        if (capacity == null) {
            return null;
        }
        return capacity.getId();
    }

    @Override
    @LogInvocation
    public Room.Capacity convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }
        return Stream.of(Room.Capacity.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
