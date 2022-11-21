package com.company.hotel_booking.service.mapper;

import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface for mapping between User and UserDto
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Method transforms object User into object UserDto
     *
     * @param user object User
     * @return object UserDto
     */
    @LogInvocationServer
    @Mapping(source = "role", target = "role")
    UserDto toDto(User user);

    /**
     * Method transforms object UserDto into object User
     *
     * @param userDto object UserDto
     * @return object User
     */
    @LogInvocationServer
    @Mapping(source = "role", target = "role")
    User toEntity (UserDto userDto);

}