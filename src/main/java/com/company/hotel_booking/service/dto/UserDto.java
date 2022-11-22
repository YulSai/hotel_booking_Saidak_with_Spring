package com.company.hotel_booking.service.dto;

import com.company.hotel_booking.utils.constants.ValidationConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "{msg.error.username.empty}")
    @Pattern(regexp = ValidationConstants.USERNAME, message = "{msg.error.username.format}")
    private String username;

    @NotBlank(message = "{msg.error.password.empty}")
    @Pattern(regexp = ValidationConstants.PASSWORD, message = "{msg.error.password.format}")
    @Size(min = 6, message = "{msg.error.password.length}")
    @ToString.Exclude
    private String password;

    @NotBlank(message = "{msg.error.first.name.empty}")
    @Pattern(regexp = ValidationConstants.NAME, message = "{msg.error.first.name.format}")
    private String firstName;

    @NotBlank(message = "{msg.error.last.name.empty}")
    @Pattern(regexp = ValidationConstants.NAME, message = "{msg.error.last.name.format}")
    private String lastName;

    @NotBlank(message = "{msg.error.email.empty}")
    @Email(message = "{msg.error.email.format}")
    private String email;

    @NotBlank(message = "{msg.error.phone.empty}")
    @Pattern(regexp = ValidationConstants.PHONE, message = "{msg.error.phone.format}")
    @Size(min = 10, message = "{msg.error.phone.length}")
    private String phoneNumber;

    private RoleDto role;
    private String avatar;
    private boolean block;

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

    public enum RoleDto implements GrantedAuthority {
        ADMIN,
        CLIENT;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}