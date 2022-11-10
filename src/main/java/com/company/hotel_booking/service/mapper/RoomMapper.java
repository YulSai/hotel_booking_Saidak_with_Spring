package com.company.hotel_booking.service.mapper;

import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface for mapping between Room and RoomDto
 */
@Mapper(componentModel = "spring")
public interface RoomMapper {

    /**
     * Method transforms object Room into object RoomDto
     *
     * @param room object Room
     * @return object RoomDto
     */
    @LogInvocationServer
    @ServiceEx
    @Mapping(source = "type", target = "type")
    @Mapping(source = "capacity", target = "capacity")
    @Mapping(source = "status", target = "status")
    RoomDto toDto(Room room);

    /**
     * Method transforms object RoomDto into object Room
     *
     * @param roomDto object RoomDto
     * @return object Room
     */
    @LogInvocationServer
    @ServiceEx
    @Mapping(source = "type", target = "type")
    @Mapping(source = "capacity", target = "capacity")
    @Mapping(source = "status", target = "status")
    Room toEntity (RoomDto roomDto);

}