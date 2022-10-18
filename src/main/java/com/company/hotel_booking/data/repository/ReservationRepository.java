package com.company.hotel_booking.data.repository;

import com.company.hotel_booking.data.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Interface extends JpaRepository interface for managing Reservation entities
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Method gets list of reservations starting from begin position in the table by user
     *
     * @param pageable an instance of interface Pageable for pagination information
     * @param id       user's id
     * @return List of reservations by user
     */
    Page<Reservation> findAllByUserId(Pageable pageable, Long id);

    /**
     * Method gets list of reservations by user
     *
     * @param id user's id
     * @return List of reservations by user
     */
    List<Reservation> findByUserId(Long id);
}