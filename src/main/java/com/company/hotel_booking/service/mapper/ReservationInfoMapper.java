package com.company.hotel_booking.service.mapper;

import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoomMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ReservationInfoMapper {

    /**
     * Method transforms object ReservationInfo into objectReservationInfoDto
     *
     * @param reservationInfo object ReservationInfo
     * @return object RReservationInfoDto
     */
    @LogInvocationServer
    @ServiceEx
    @Mapping(target = "reservation", ignore = true)
    ReservationInfoDto toDto(ReservationInfo reservationInfo);

    /**
     * Method transforms object ReservationInfoDto into object ReservationInfo
     *
     * @param reservationInfoDto object ReservationInfoDto
     * @return object ReservationInfo
     */
    @LogInvocationServer
    @ServiceEx
    @Mapping(target = "reservation", ignore = true)
    ReservationInfo toEntity(ReservationInfoDto reservationInfoDto);
}