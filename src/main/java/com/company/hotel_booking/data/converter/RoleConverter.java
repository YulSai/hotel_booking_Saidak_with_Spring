package com.company.hotel_booking.data.converter;

import com.company.hotel_booking.data.entity.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * Class for mapping an enum Role of user value to its database representation
 */
@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<User.Role, Long> {
    @Override
    public Long convertToDatabaseColumn(User.Role role) {
        if (role == null) {
            return null;
        }
        return role.getId();
    }

    @Override
    public User.Role convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }
        return Stream.of(User.Role.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
