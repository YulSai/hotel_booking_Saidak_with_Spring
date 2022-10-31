package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.service.mapper.ReservationInfoMapper;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.data.repository.ReservationInfoRepository;
import com.company.hotel_booking.service.api.ReservationInfoService;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.utils.exceptions.reservationsInfo.ReservationInfoDeleteException;
import com.company.hotel_booking.utils.exceptions.reservationsInfo.ReservationInfoNotFoundException;
import com.company.hotel_booking.utils.exceptions.reservationsInfo.ReservationInfoServiceException;
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
    private final ReservationInfoMapper mapper;

    @Override
    @LogInvocationServer
    @ServiceEx
    public ReservationInfoDto findById(Long id) {
        return mapper.toDto(reservationInfoRepository.findById(id).orElseThrow(
                () -> new ReservationInfoNotFoundException("msg.reservation.info.error.find.by.id")));
    }

    @Override
    @LogInvocationServer
    @Transactional
    public ReservationInfoDto create(ReservationInfoDto entity) {
        ReservationInfoDto info = mapper.toDto(reservationInfoRepository.save(mapper.toEntity(entity)));
        if (info == null) {
            throw new ReservationInfoServiceException("msg.reservation.info.error.create");
        }
        return info;
    }

    @Override
    @LogInvocationServer
    public ReservationInfoDto update(ReservationInfoDto entity) {
        ReservationInfoDto info = mapper.toDto(reservationInfoRepository.save(mapper.toEntity(entity)));
        if (info == null) {
            throw new ReservationInfoServiceException("msg.reservation.info.error.update");
        }
        return info;
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(ReservationInfoDto reservationInfoDto) {
        reservationInfoRepository.delete(mapper.toEntity(reservationInfoDto));
        if (reservationInfoRepository.existsById(reservationInfoDto.getId())) {
            throw new ReservationInfoDeleteException("msg.reservation.info.error.delete");
        }
    }

    @Override
    @LogInvocationServer
    public Page<ReservationInfoDto> findAllPages(Pageable pageable) {
        return reservationInfoRepository.findAll(pageable)
                .map(mapper::toDto);
    }
}