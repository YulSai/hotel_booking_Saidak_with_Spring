package com.company.hotel_booking.data.converter;

import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * Class for mapping an enum Status of reservation value to its database representation
 */
@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Reservation.Status, Long> {
    @Override
    @LogInvocation
    public Long convertToDatabaseColumn(Reservation.Status status) {
        if (status == null) {
            return null;
        }
        return status.getId();
    }

    @Override
    @LogInvocation
    public Reservation.Status convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }
        return Stream.of(Reservation.Status.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
