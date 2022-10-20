package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.data.repository.ReservationInfoRepository;
import com.company.hotel_booking.service.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.ReservationInfoService;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        return mapper.toDto(reservationInfoRepository.findById(id).orElseThrow(
                () -> new ServiceException(MessageManager.getMessage
                        ("msg.reservation.info.error.find.by.id") + id)));
    }

    @Override
    @LogInvocationServer
    @Transactional
    public ReservationInfoDto create(ReservationInfoDto entity) {
        ReservationInfoDto info = mapper.toDto(reservationInfoRepository.save(mapper.toEntity(entity)));
        if (info == null) {
            throw new ServiceException(MessageManager.getMessage("msg.reservation.info.error.create") + entity);
        }
        return info;
    }

    @Override
    @LogInvocationServer
    public ReservationInfoDto update(ReservationInfoDto entity) {
        ReservationInfoDto info = mapper.toDto(reservationInfoRepository.save(mapper.toEntity(entity)));
        if (info == null) {
            throw new ServiceException(MessageManager.getMessage("msg.reservation.info.error.update") + entity);
        }
        return info;
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(ReservationInfoDto reservationInfoDto) {
        reservationInfoRepository.delete(mapper.toEntity(reservationInfoDto));
        if (reservationInfoRepository.existsById(reservationInfoDto.getId())) {
            throw new ServiceException(
                    MessageManager.getMessage("msg.reservation.info.error.delete") + reservationInfoDto.getId());
        }
    }

    @Override
    @LogInvocationServer
    public Page<ReservationInfoDto> findAllPages(Pageable pageable) {
        return reservationInfoRepository.findAll(pageable)
                .map(mapper::toDto);
    }
}