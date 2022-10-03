package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.data.dao.api.IReservationInfoDao;
import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.data.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.service.api.IReservationInfoService;
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
public class ReservationInfoServiceImpl implements IReservationInfoService {
    private final IReservationInfoDao reservationInfoDao;
    private final ObjectMapper mapper;

    @Override
    public ReservationInfoDto findById(Long id) {
        log.debug("Calling a service method findById. ReservationInfoDto id = {}", id);
        ReservationInfo reservationInfo = reservationInfoDao.findById(id);
        if (reservationInfo == null) {
            log.error("SQLUserService findById error. No with id = {}", id);
            throw new ServiceException(MessageManger.getMessage("msg.error.find") + id);
        }
        return mapper.toDto(reservationInfo);
    }

    @Override
    public List<ReservationInfoDto> findAll() {
        log.debug("Calling a service method findAll");
        return reservationInfoDao.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ReservationInfoDto create(ReservationInfoDto entity) {
        log.debug("Calling a service method reate. ReservationInfo = {}", entity);
        return mapper.toDto(reservationInfoDao.save(mapper.toEntity(entity)));
    }

    @Override
    public List<ReservationInfoDto> processBookingInfo(Map<Long, Long> booking, LocalDate checkIn,
                                                       LocalDate checkOut, ReservationDto reservation) {
        log.debug("Calling a service method processBookingInfo");
        return reservationInfoDao.processBookingInfo(booking, checkIn, checkOut, reservation).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ReservationInfoDto update(ReservationInfoDto entity) {
        log.debug("Calling a service method update. ReservationInfo = {}", entity);
        return mapper.toDto(reservationInfoDao.update(mapper.toEntity(entity)));
    }

    @Override
    public void delete(Long id) {
        log.debug("Calling a service method delete. ReservationInfo id = {}", id);
        reservationInfoDao.delete(id);
        if (!reservationInfoDao.delete(id)) {
            log.error("SQLReservationService deleted error. Failed to delete reservation info with id = {}", id);
            throw new ServiceException("Failed to delete reservation info with id " + id);
        }
    }

    @Override
    public List<ReservationInfoDto> findAllPages(Paging paging) {
        log.debug("Calling a service method indAllPages");
        return reservationInfoDao.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public long countRow() {
        log.debug("Calling a service method countRow");
        return reservationInfoDao.countRow();
    }
}