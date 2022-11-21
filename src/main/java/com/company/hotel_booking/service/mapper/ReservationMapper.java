package com.company.hotel_booking.service.mapper;

import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

/**
 * Interface for mapping between Reservation and ReservationDto
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ReservationInfoMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ReservationMapper {

    /**
     * Method transforms object Reservation into object ReservationDto
     *
     * @param reservation object Reservation
     * @return object ReservationDto
     */
    @LogInvocationServer
    ReservationDto toDto(Reservation reservation);

    /**
     * Method transforms object ReservationDto into object Reservation
     *
     * @param reservationDto object ReservationDto
     * @return object Reservation
     */
    @LogInvocationServer
    Reservation toEntity(ReservationDto reservationDto);
}