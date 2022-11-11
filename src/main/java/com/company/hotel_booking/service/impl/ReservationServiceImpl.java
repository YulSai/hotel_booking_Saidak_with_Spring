package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.data.repository.ReservationRepository;
import com.company.hotel_booking.data.repository.RoomRepository;
import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.service.mapper.ReservationMapper;
import com.company.hotel_booking.service.mapper.RoomMapper;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.utils.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.utils.exceptions.reservations.ReservationDeleteException;
import com.company.hotel_booking.utils.exceptions.reservations.ReservationNotFoundException;
import com.company.hotel_booking.utils.exceptions.reservations.ReservationServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class object ReservationDTO with implementation of CRUD operation operations
 */
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ReservationMapper mapper;
    private final RoomMapper roomMapper;
    private final MessageSource messageSource;

    @Override
    @LogInvocationServer
    @NotFoundEx
    public ReservationDto findById(Long id) {
        return mapper.toDto(reservationRepository.findById(id).orElseThrow(
                () -> new ReservationNotFoundException(
                        messageSource.getMessage("msg.reservation.error.find.by.id", null,
                                LocaleContextHolder.getLocale()))));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public ReservationDto create(ReservationDto entity) {
        entity.setStatus(ReservationDto.StatusDto.CONFIRMED);
        ReservationDto reservation = mapper.toDto(reservationRepository.save(mapper.toEntity(entity)));
        if (reservation == null) {
            throw new ReservationServiceException(
                    messageSource.getMessage("msg.reservation.error.create", null,
                            LocaleContextHolder.getLocale()));
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
            RoomDto room = roomMapper.toDto(roomRepository.findById(roomId).orElse(null));
            info.setRoom(room);
            info.setCheckIn(checkIn);
            info.setCheckOut(checkOut);
            info.setNights(ChronoUnit.DAYS.between(checkIn, checkOut));
            info.setRoomPrice(room.getPrice());
            info.setReservation(reservation);
            details.add(info);
        });
        BigDecimal totalCost = calculatePrice(details);
        reservation.setTotalCost(totalCost);
        reservation.setDetails(details);
        return reservation;
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public ReservationDto processReservationCreation(Map<Long, Long> booking, UserDto user, LocalDate checkIn,
                                                     LocalDate checkOut) {
        ReservationDto reservation = processBooking(booking, user, checkIn, checkOut);
        return create(reservation);
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
    @ServiceEx
    public ReservationDto update(ReservationDto entity) {
        ReservationDto reservation = mapper.toDto(reservationRepository.save(mapper.toEntity(entity)));
        if (reservation == null) {
            throw new ReservationServiceException(
                    messageSource.getMessage("msg.reservation.error.update", null,
                            LocaleContextHolder.getLocale()));
        }
        return reservation;
    }

    @Override
    @LogInvocationServer
    @Transactional
    @ServiceEx
    public void delete(ReservationDto reservationDto) {
        reservationRepository.delete(mapper.toEntity(reservationDto));
        if (reservationRepository.existsById(reservationDto.getId())) {
            throw new ReservationDeleteException(messageSource.getMessage("msg.reservation.error.delete", null,
                    LocaleContextHolder.getLocale()));
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