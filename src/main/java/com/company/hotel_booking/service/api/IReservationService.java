package com.company.hotel_booking.service.api;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface for serving reservation objects according to the business logics of reservation
 */
public interface
IReservationService extends IAbstractService<Long, ReservationDto> {
    /**
     * Method gets list of reservations starting from begin position in the table by user
     *
     * @param paging object Paging
     * @param id     user's id
     * @return List of reservations by user
     */
    List<ReservationDto> findAllPagesByUsers(Paging paging, Long id);

    /**
     * Method processes information about booking
     *
     * @param booking  Map collection with booking data
     * @param user     user who is booking
     * @param checkIn  booking start date
     * @param checkOut booking end date
     * @return list of reservation
     */
    ReservationDto processBooking(Map<Long, Long> booking, UserDto user, LocalDate checkIn,
                                  LocalDate checkOut);

    /**
     * Method gets list of reservations by user
     *
     * @param id user's id
     * @return List of reservations by user
     */
    List<ReservationDto> findAllByUsers(Long id);
}