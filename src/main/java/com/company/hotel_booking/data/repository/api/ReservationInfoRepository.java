package com.company.hotel_booking.data.repository.api;

import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.service.dto.ReservationDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/*
 * Interface extends IAbstractDao interface for managing ReservationInfo entities
 */
public interface ReservationInfoRepository extends AbstractRepository<Long, ReservationInfo> {

    /**
     * Method processes information about booking
     *
     * @param booking     Map collection with booking data
     * @param checkIn     booking start date
     * @param checkOut    booking end date
     * @param reservation order
     * @return list of reservation info
     */
    List<ReservationInfo> processBookingInfo(Map<Long, Long> booking, LocalDate checkIn,
                                             LocalDate checkOut, ReservationDto reservation);
}