package com.company.hotel_booking.dao.api;

import com.company.hotel_booking.dao.entity.Reservation;

import java.util.List;

/*
 * Interface extends IAbstractDao interface for managing Reservation entities
 */
public interface IReservationDao extends IAbstractDao<Long, Reservation> {

    /**
     * Method gets list of reservations starting from begin position in the table by user
     *
     * @param limit  number of records from the table
     * @param offset starting position for search in the table
     * @param id     user's id
     * @return List of reservations by user
     */
    List<Reservation> findAllPagesByUsers(int limit, long offset, Long id);

    /**
     * Method gets list of reservations by user
     *
     * @param id user's id
     * @return List of reservations by user
     */
    List<Reservation> findAllByUsers(Long id);
}