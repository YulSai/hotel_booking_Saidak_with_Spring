package com.company.hotel_booking.data.repository.impl;

import com.company.hotel_booking.data.mapper.ObjectMapper;
import com.company.hotel_booking.data.repository.api.ReservationInfoRepository;
import com.company.hotel_booking.data.repository.api.RoomRepository;
import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.managers.SqlManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class object ReservationInfoDao with implementation of CRUD operation operations
 */
@Log4j2
@Repository
@Transactional
@RequiredArgsConstructor
public class ReservationInfoRepositoryImpl implements ReservationInfoRepository {
    private final RoomRepository roomRepository;
    private final ObjectMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ReservationInfo findById(Long id) {
        log.debug("Accessing the database using the findById command. ReservationInfo id = {}", id);
        try {
            return entityManager.find(ReservationInfo.class, id);
        } catch (HibernateException e) {
            log.error("SQLReservationInfo findById error: = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.by.id") + id);
        }
    }

    @Override
    public List<ReservationInfo> findAll() {
        log.debug("Accessing the database using the findAll command");
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_INFO_FIND_ALL,
                    ReservationInfo.class).getResultList();
        } catch (HibernateException e) {
            log.error("SQLReservationInfoDAO findAll", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.all"));
        }
    }

    @Override
    public ReservationInfo create(ReservationInfo reservationInfo) {
        log.debug("Accessing the database using the save command");
        try {
            entityManager.persist(reservationInfo);
            return reservationInfo;
        } catch (HibernateException | NullPointerException e) {
            log.error("SQLReservationInfoDAO save error: " + reservationInfo, e);
            throw new DaoException(MessageManager.getMessage("msg.error.create") + reservationInfo);
        }
    }

    @Override
    @Transactional
    public List<ReservationInfo> processBookingInfo(Map<Long, Long> booking, LocalDate checkIn,
                                                    LocalDate checkOut, ReservationDto reservation) {
        log.debug("Accessing the database using the processBookingInfo command");
        List<ReservationInfo> reservationsInfo = new ArrayList<>();
        booking.forEach((roomId, quantity) -> {
            ReservationInfo info = new ReservationInfo();
            info.setReservation(mapper.toEntity(reservation));
            Room room = roomRepository.findById(roomId);
            info.setRoom(room);
            info.setCheckIn(checkIn);
            info.setCheckOut(checkOut);
            info.setNights(ChronoUnit.DAYS.between(checkIn, checkOut));
            info.setRoomPrice(room.getPrice());
            reservationsInfo.add(info);
            create(info);
        });
        return reservationsInfo;
    }


    @Override
    public ReservationInfo update(ReservationInfo reservationInfo) {
        log.debug("Accessing the database using the update command. ReservationInfo = {}", reservationInfo);
        try {
            entityManager.merge(reservationInfo);
            return reservationInfo;
        } catch (HibernateException e) {
            log.error("Command update can't be executed");
            throw new DaoException(MessageManager.getMessage("msg.error.command") + reservationInfo);
        }
    }

    @Override
    public int delete(Long id) {
        log.debug("Accessing the database using the delete command. ReservationInfo id = {}", id);
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_INFO_DELETE).setParameter("id",
                    id).executeUpdate();
        } catch (HibernateException e) {
            log.error("SQLReservationInfoDAO delete error id  = {}", id, e);
            throw new DaoException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<ReservationInfo> findAllPages(int limit, long offset) {
        log.debug("Accessing the database using the findAllPages command");
        try {
            return entityManager.createQuery(SqlManager.SQL_RESERVATION_INFO_PAGE, ReservationInfo.class)
                    .setMaxResults(limit)
                    .setFirstResult((int) offset)
                    .getResultList();
        } catch (HibernateException e) {
            log.error("SQLReservationInfoDAO findAllPages error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find"));
        }
    }

    @Override
    public Long countRow() throws DaoException {
        log.debug("Accessing the database using the findRowCount command");
        try {
            return (Long) entityManager.createQuery(
                    SqlManager.SQL_RESERVATION_INFO_COUNT_RESERVATIONS_INFO).getSingleResult();
        } catch (HibernateException e) {
            log.error("SQLReservationInfoDAO findRowCount error", e);
            throw new DaoException(MessageManager.getMessage("msg.error.find.count"));
        }
    }
}