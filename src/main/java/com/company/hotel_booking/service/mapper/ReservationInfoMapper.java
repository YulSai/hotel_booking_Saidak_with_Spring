package com.company.hotel_booking.service.mapper;

import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface for mapping between ReservationInfo and ReservationInfoDto
 */
@Mapper(componentModel = "spring", uses = {RoomMapper.class})
public interface ReservationInfoMapper {

    /**
     * Method transforms object ReservationInfo into objectReservationInfoDto
     *
     * @param reservationInfo object ReservationInfo
     * @return object RReservationInfoDto
     */
    @LogInvocationServer
    @Mapping(target = "reservation", ignore = true)
    ReservationInfoDto toDto(ReservationInfo reservationInfo);

    /**
     * Method transforms object ReservationInfoDto into object ReservationInfo
     *
     * @param reservationInfoDto object ReservationInfoDto
     * @return object ReservationInfo
     */
    @LogInvocationServer
    @Mapping(target = "reservation", ignore = true)
    ReservationInfo toEntity(ReservationInfoDto reservationInfoDto);
}