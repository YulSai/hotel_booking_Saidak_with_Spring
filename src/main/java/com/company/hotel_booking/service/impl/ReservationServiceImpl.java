package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.data.dao.api.IReservationDao;
import com.company.hotel_booking.data.dao.api.IRoomDao;
import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.data.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.IReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Class object ReservationDTO with implementation of CRUD operation operations
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationDao reservationDao;
    private final IRoomDao roomDao;
    private final ObjectMapper mapper;

    @Override
    public ReservationDto findById(Long id) {
        log.debug("Calling a service method findById. Reservation id = {}", id);
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            log.error("SQLReservationService findById error. id = {}", id);
            throw new ServiceException(MessageManager.getMessage("msg.empty") + id);
        }
        return mapper.toDto(reservation);
    }

    public List<ReservationDto> findAll() {
        log.debug("Calling a service method findAll");
        return reservationDao.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ReservationDto create(ReservationDto entity) {
        log.debug("Calling a service method create. Reservation = {}", entity);
        entity.setStatus(ReservationDto.StatusDto.CONFIRMED);

        return mapper.toDto(reservationDao.save(mapper.toEntity(entity)));
    }

    @Override
    public ReservationDto processBooking(Map<Long, Long> booking, UserDto user, LocalDate checkIn,
                                         LocalDate checkOut) {
        ReservationDto reservation = new ReservationDto();
        reservation.setUser(user);
        reservation.setStatus(ReservationDto.StatusDto.IN_PROGRESS);
        List<ReservationInfoDto> details = new ArrayList<>();
        booking.forEach((roomId, quantity) -> {
            ReservationInfoDto info = new ReservationInfoDto();
            RoomDto room = mapper.toDto(roomDao.findById(roomId));
            info.setRoom(room);
            info.setCheckIn(checkIn);
            info.setCheckOut(checkOut);
            info.setNights(ChronoUnit.DAYS.between(checkIn, checkOut));
            info.setRoomPrice(room.getPrice());
            details.add(info);
        });
        reservation.setDetails(details);
        BigDecimal totalCost = calculatePrice(details);
        reservation.setTotalCost(totalCost);
        return reservation;
    }

    private BigDecimal calculatePrice(List<ReservationInfoDto> details) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (ReservationInfoDto detail : details) {
            BigDecimal roomPrice = detail.getRoomPrice();
            Long nights = ChronoUnit.DAYS.between(detail.getCheckIn(), detail.getCheckOut());
            totalCost = totalCost.add(roomPrice.multiply(BigDecimal.valueOf(nights)));
        }
        return totalCost;
    }

    @Override
    public ReservationDto update(ReservationDto entity) {
        log.debug("Calling a service method update. Reservation = {}", entity);
        return mapper.toDto(reservationDao.update(mapper.toEntity(entity)));
    }

    @Override
    public void delete(Long id) {
        log.debug("Calling a service method delete. Reservation id = {}", id);
        reservationDao.delete(id);
        if (!reservationDao.delete(id)) {
            log.error("SQLReservationService deleted error. Failed to delete reservation with id = {}", id);
            throw new ServiceException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<ReservationDto> findAllPages(Paging paging) {
        log.debug("Calling a service method findAllPages");
        return reservationDao.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ReservationDto> findAllPagesByUsers(Paging paging, Long id) {
        log.debug("Calling a service method findAllPagesByUsers");
        return reservationDao.findAllPagesByUsers(paging.getLimit(), paging.getOffset(), id).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public long countRow() {
        log.debug("Calling a service method countRow");
        return reservationDao.countRow();
    }

    @Override
    public List<ReservationDto> findAllByUsers(Long id) {
        log.debug("Calling a service method findAllPagesByUsers");
        return reservationDao.findAllByUsers(id).stream()
                .map(mapper::toDto)
                .toList();
    }
}