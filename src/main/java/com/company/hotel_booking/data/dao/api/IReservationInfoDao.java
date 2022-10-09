package com.company.hotel_booking.data.dao.api;

import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.service.dto.ReservationDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/*
 * Interface extends IAbstractDao interface for managing ReservationInfo entities
 */
public interface IReservationInfoDao extends IAbstractDao<Long, ReservationInfo> {

    /**
     * Method finds ReservationInfo object in the data source by reservation id
     *
     * @param id reservation id
     */
    List<ReservationInfo> findByReservationId(Long id);

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