package com.company.hotel_booking.data.repository;

import com.company.hotel_booking.data.entity.ReservationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Interface extends JpaRepository interface for managing ReservationInfo entities
 */
public interface ReservationInfoRepository extends JpaRepository<ReservationInfo, Long> {
}