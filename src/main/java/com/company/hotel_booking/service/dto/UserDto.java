package com.company.hotel_booking.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * Class describing the object UserDto
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private RoleDto role;
    private String avatar;

    public enum RoleDto {
        ADMIN,
        CLIENT,
        GUEST
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserDto user = (UserDto) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}