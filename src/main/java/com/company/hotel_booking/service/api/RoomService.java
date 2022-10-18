package com.company.hotel_booking.service.api;

import com.company.hotel_booking.service.dto.RoomDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for serving Room objects according to the business logics of Room
 */
public interface RoomService extends AbstractService<Long, RoomDto> {
    /**
     * Method gets list of an available Rooms limited by time period
     *
     * @param check_in  start date to search
     * @param check_out end date to search
     * @return List of reservations
     */
    List<RoomDto> findAvailableRooms(Long typeId, Long capacityId, LocalDate check_in, LocalDate check_out);
}