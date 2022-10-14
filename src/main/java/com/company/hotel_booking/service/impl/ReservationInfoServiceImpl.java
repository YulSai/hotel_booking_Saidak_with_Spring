package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.aspects.logging.LogInvocationServer;
import com.company.hotel_booking.aspects.logging.ServiceEx;
import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.data.repository.api.ReservationInfoRepository;
import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.service.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.ReservationInfoService;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Class object ReservationInfoDTO with implementation of CRUD operation operations
 */
@Service
@RequiredArgsConstructor
public class ReservationInfoServiceImpl implements ReservationInfoService {
    private final ReservationInfoRepository reservationInfoRepository;
    private final ObjectMapper mapper;

    @Override
    @LogInvocationServer
    @ServiceEx
    public ReservationInfoDto findById(Long id) {
        ReservationInfo reservationInfo = reservationInfoRepository.findById(id);
        if (reservationInfo == null) {
            throw new ServiceException(MessageManager.getMessage("msg.error.find") + id);
        }
        return mapper.toDto(reservationInfo);
    }

    @Override
    @LogInvocationServer
    public List<ReservationInfoDto> findAll() {
        return reservationInfoRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @LogInvocationServer
    @Transactional
    public ReservationInfoDto create(ReservationInfoDto entity) {
        return mapper.toDto(reservationInfoRepository.create(mapper.toEntity(entity)));
    }

    @Override
    @LogInvocationServer
    public ReservationInfoDto update(ReservationInfoDto entity) {
        return mapper.toDto(reservationInfoRepository.update(mapper.toEntity(entity)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(Long id) {
        reservationInfoRepository.delete(id);
        if (reservationInfoRepository.delete(id) != 1) {
            throw new ServiceException("Failed to delete reservation info with id " + id);
        }
    }

    @Override
    @LogInvocationServer
    public List<ReservationInfoDto> findAllPages(Paging paging) {
        return reservationInfoRepository.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @LogInvocationServer
    public long countRow() {
        return reservationInfoRepository.countRow();
    }
}