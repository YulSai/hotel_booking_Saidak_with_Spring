package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.data.repository.api.ReservationInfoRepository;
import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.data.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.ReservationInfoService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Class object ReservationInfoDTO with implementation of CRUD operation operations
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ReservationInfoServiceImpl implements ReservationInfoService {
    private final ReservationInfoRepository reservationInfoRepository;
    private final ObjectMapper mapper;

    @Override
    public ReservationInfoDto findById(Long id) {
        log.debug("Calling a service method findById. ReservationInfoDto id = {}", id);
        ReservationInfo reservationInfo = reservationInfoRepository.findById(id);
        if (reservationInfo == null) {
            log.error("SQLUserService findById error. No with id = {}", id);
            throw new ServiceException(MessageManager.getMessage("msg.error.find") + id);
        }
        return mapper.toDto(reservationInfo);
    }

    @Override
    public List<ReservationInfoDto> findAll() {
        log.debug("Calling a service method findAll");
        return reservationInfoRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ReservationInfoDto create(ReservationInfoDto entity) {
        log.debug("Calling a service method create. ReservationInfo = {}", entity);
        return mapper.toDto(reservationInfoRepository.create(mapper.toEntity(entity)));
    }

    @Override
    public List<ReservationInfoDto> processBookingInfo(Map<Long, Long> booking, LocalDate checkIn,
                                                       LocalDate checkOut, ReservationDto reservation) {
        log.debug("Calling a service method processBookingInfo");
        return reservationInfoRepository.processBookingInfo(booking, checkIn, checkOut, reservation).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ReservationInfoDto update(ReservationInfoDto entity) {
        log.debug("Calling a service method update. ReservationInfo = {}", entity);
        return mapper.toDto(reservationInfoRepository.update(mapper.toEntity(entity)));
    }

    @Override
    public void delete(Long id) {
        log.debug("Calling a service method delete. ReservationInfo id = {}", id);
        reservationInfoRepository.delete(id);
        if (reservationInfoRepository.delete(id) != 1) {
            log.error("SQLReservationService deleted error. Failed to delete reservation info with id = {}", id);
            throw new ServiceException("Failed to delete reservation info with id " + id);
        }
    }

    @Override
    public List<ReservationInfoDto> findAllPages(Paging paging) {
        log.debug("Calling a service method indAllPages");
        return reservationInfoRepository.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public long countRow() {
        log.debug("Calling a service method countRow");
        return reservationInfoRepository.countRow();
    }
}