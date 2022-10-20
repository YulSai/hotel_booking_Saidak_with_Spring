package com.company.hotel_booking.service.api;

import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface for serving reservation objects according to the business logics of reservation
 */
public interface
ReservationService extends AbstractService<Long, ReservationDto> {
    /**
     * Method gets list of reservations starting from begin position in the table by user
     *
     * @param pageable an instance of interface Pageable for pagination information
     * @param id     user's id
     * @return List of reservations by user
     */
    Page<ReservationDto> findAllPagesByUsers(Pageable pageable, Long id);

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