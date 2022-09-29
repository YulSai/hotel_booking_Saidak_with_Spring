package com.company.hotel_booking.service.dto;

import lombok.Data;

/**
 * Class describing the object UserDto
 */
@Data
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
}