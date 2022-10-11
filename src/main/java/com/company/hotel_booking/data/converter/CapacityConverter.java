package com.company.hotel_booking.data.converter;

import com.company.hotel_booking.data.entity.Room;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CapacityConverter implements AttributeConverter<Room.Capacity, Long> {
    @Override
    public Long convertToDatabaseColumn(Room.Capacity capacity) {
        if (capacity == null) {
            return null;
        }
        return capacity.getId();
    }

    @Override
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
