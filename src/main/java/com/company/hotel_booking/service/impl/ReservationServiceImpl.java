package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.data.repository.api.ReservationRepository;
import com.company.hotel_booking.data.repository.api.RoomRepository;
import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.service.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Class object ReservationDTO with implementation of CRUD operation operations
 */
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ObjectMapper mapper;

    @Override
    @LogInvocationServer
    @ServiceEx
    public ReservationDto findById(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new ServiceException(MessageManager.getMessage("msg.empty") + id);
        }
        return mapper.toDto(reservation);
    }

    @Override
    @LogInvocationServer
    public List<ReservationDto> findAll() {
        return reservationRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @LogInvocationServer
    @Transactional
    public ReservationDto create(ReservationDto entity) {
        entity.setStatus(ReservationDto.StatusDto.CONFIRMED);
        return mapper.toDto(reservationRepository.create(mapper.toEntity(entity)));
    }

    @Override
    @LogInvocationServer
    public ReservationDto processBooking(Map<Long, Long> booking, UserDto user, LocalDate checkIn,
                                         LocalDate checkOut) {
        ReservationDto reservation = new ReservationDto();
        reservation.setUser(user);
        reservation.setStatus(ReservationDto.StatusDto.IN_PROGRESS);
        List<ReservationInfoDto> details = new ArrayList<>();
        booking.forEach((roomId, quantity) -> {
            ReservationInfoDto info = new ReservationInfoDto();
            RoomDto room = mapper.toDto(roomRepository.findById(roomId));
            info.setRoom(room);
            info.setCheckIn(checkIn);
            info.setCheckOut(checkOut);
            info.setNights(ChronoUnit.DAYS.between(checkIn, checkOut));
            info.setRoomPrice(room.getPrice());
            info.setReservationDto(reservation);
            details.add(info);
        });
        BigDecimal totalCost = calculatePrice(details);
        reservation.setTotalCost(totalCost);
        reservation.setDetails(details);
        return reservation;
    }

    @LogInvocationServer
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
    @LogInvocationServer
    @Transactional
    public ReservationDto update(ReservationDto entity) {
        return mapper.toDto(reservationRepository.update(mapper.toEntity(entity)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(Long id) {
        reservationRepository.delete(id);
        if (reservationRepository.delete(id) != 1) {
            throw new ServiceException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    @LogInvocationServer
    public List<ReservationDto> findAllPages(Paging paging) {
        return reservationRepository.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @LogInvocationServer
    public List<ReservationDto> findAllPagesByUsers(Paging paging, Long id) {
        return reservationRepository.findAllPagesByUsers(paging.getLimit(), paging.getOffset(), id).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @LogInvocationServer
    public long countRow() {
        return reservationRepository.countRow();
    }

    @Override
    @LogInvocationServer
    public List<ReservationDto> findAllByUsers(Long id) {
        return reservationRepository.findAllByUsers(id).stream()
                .map(mapper::toDto)
                .toList();
    }
}