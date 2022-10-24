package com.company.hotel_booking.data.repository;

import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.utils.managers.SqlManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface extends JpaRepository interface for managing Room entities
 */
public interface RoomRepository extends JpaRepository<Room, Long>{

    /**
     * Method finds room in the data source by its number
     *
     * @param number room number for search
     * @return room
     */
    Optional<Room> findByNumber(String number);

    /**
     * Method finds all available rooms limited by time period
     *
     * @param check_in  start date for search
     * @param check_out end date to search
     * @return list of on available rooms
     */
    @Query(value = SqlManager.SQL_ROOM_FIND_AVAILABLE_ROOMS, nativeQuery = true)
    List<Room> findAvailableRooms(Long typeId, Long capacityId, LocalDate check_in, LocalDate check_out,
                                  LocalDate check_in_double, LocalDate check_out_double);
}