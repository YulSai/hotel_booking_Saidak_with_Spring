package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.data.repository.ReservationRepository;
import com.company.hotel_booking.data.repository.RoomRepository;
import com.company.hotel_booking.service.mapper.ObjectMapper;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import com.company.hotel_booking.utils.managers.MessageManager;
import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return mapper.toDto(reservationRepository.findById(id).orElseThrow(
                () -> new ServiceException(MessageManager.getMessage("msg.reservation.error.find.by.id") + id)));
    }

    @Override
    @LogInvocationServer
    @Transactional
    public ReservationDto create(ReservationDto entity) {
        entity.setStatus(ReservationDto.StatusDto.CONFIRMED);
        ReservationDto reservation = mapper.toDto(reservationRepository.save(mapper.toEntity(entity)));
        if (reservation == null) {
            throw new ServiceException(MessageManager.getMessage("msg.reservation.error.create") + entity);
        }
        return reservation;
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
            RoomDto room = mapper.toDto(roomRepository.findById(roomId).get());
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
        ReservationDto reservation = mapper.toDto(reservationRepository.save(mapper.toEntity(entity)));
        if (reservation == null) {
            throw new ServiceException(MessageManager.getMessage("msg.reservation.error.update") + entity);
        }
        return reservation;
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(ReservationDto reservationDto) {
        reservationRepository.delete(mapper.toEntity(reservationDto));
        if (reservationRepository.existsById(reservationDto.getId())) {
            throw new ServiceException(
                    MessageManager.getMessage("msg.reservation.error.delete") + reservationDto.getId());
        }
    }

    @Override
    @LogInvocationServer
    public Page<ReservationDto> findAllPages(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    @LogInvocationServer
    public Page<ReservationDto> findAllPagesByUsers(Pageable pageable, Long id) {
        return reservationRepository.findAllByUserId(pageable, id).map(mapper::toDto);
    }

    @Override
    @LogInvocationServer
    public List<ReservationDto> findAllByUsers(Long id) {
        return reservationRepository.findByUserId(id).stream()
                .map(mapper::toDto)
                .toList();
    }
}