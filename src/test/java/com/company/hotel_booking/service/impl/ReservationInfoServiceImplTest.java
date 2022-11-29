package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.data.repository.ReservationInfoRepository;
import com.company.hotel_booking.service.api.ReservationInfoService;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.mapper.ReservationInfoMapper;
import com.company.hotel_booking.utils.DtoTest;
import com.company.hotel_booking.utils.EntityTest;
import com.company.hotel_booking.utils.TestConstants;
import com.company.hotel_booking.utils.exceptions.reservationsInfo.ReservationInfoDeleteException;
import com.company.hotel_booking.utils.exceptions.reservationsInfo.ReservationInfoNotFoundException;
import com.company.hotel_booking.utils.exceptions.reservationsInfo.ReservationInfoServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationInfoServiceImplTest {
    private ReservationInfoService reservationInfoService;
    @Mock
    private ReservationInfoRepository reservationInfoRepository;
    @Mock
    private ReservationInfoMapper mapper;
    @Mock
    private MessageSource messageSource;

    private ReservationInfo info;
    private ReservationInfoDto infoDto;

    @BeforeEach
    public void setup() {
        reservationInfoService = new ReservationInfoServiceImpl(reservationInfoRepository, mapper, messageSource);
        info = EntityTest.getExpectedReservationInfoWithoutId();
        infoDto = DtoTest.getExpectedReservationInfoWithoutId();
    }

    private void mockMapperToEntity() {
        when(mapper.toEntity(infoDto)).thenReturn(info);
    }

    private void mockMapperToDto() {
        when(mapper.toDto(info)).thenReturn(infoDto);
    }

    @Test
    void getReservationInfoPositive() {
        when(reservationInfoRepository.findById(TestConstants.RESERVATION_INFO_ID)).thenReturn(Optional.of(info));
        mockMapperToDto();

        ReservationInfoDto actual = reservationInfoService.findById(TestConstants.RESERVATION_INFO_ID);

        assertEquals(infoDto, actual);
        verify(reservationInfoRepository, times(1)).findById(TestConstants.RESERVATION_INFO_ID);
        verify(mapper, times(1)).toDto(any(ReservationInfo.class));
    }

    @Test
    void getReservationInfoNotFound() {
        when(reservationInfoRepository.findById(TestConstants.RESERVATION_INFO_ID)).thenReturn(Optional.empty());

        assertThrows(ReservationInfoNotFoundException.class,
                () -> reservationInfoService.findById(TestConstants.RESERVATION_INFO_ID));
        verify(mapper, never()).toDto(any(ReservationInfo.class));
    }

    @Test
    void getAllReservationInfoPositive() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ReservationInfo> pageRoom = new PageImpl<>(new ArrayList<>());
        Page<ReservationInfoDto> pageRoomDto = new PageImpl<>(new ArrayList<>());

        when(reservationInfoRepository.findAll(pageable)).thenReturn(pageRoom);
        when(reservationInfoRepository.findAll(pageable).map(mapper::toDto)).thenReturn(pageRoomDto);

        Page<ReservationInfoDto> actual = reservationInfoService.findAllPages(pageable);
        assertNotNull(actual);
        verify(reservationInfoRepository, times(1)).findAll(pageable);
    }

    @Test
    void createReservationInfoPositive() {
        mockMapperToEntity();
        when(reservationInfoRepository.save(info)).thenReturn(info);
        mockMapperToDto();

        ReservationInfoDto actual = reservationInfoService.create(infoDto);

        assertEquals(infoDto, actual);
        verify(reservationInfoRepository, times(1)).save(any(ReservationInfo.class));
        verify(mapper, times(1)).toEntity(any(ReservationInfoDto.class));
        verify(mapper, times(1)).toDto(any(ReservationInfo.class));
    }

    @Test
    void createReservationInfoReservationInfoServiceException() {
        mockMapperToEntity();
        when(reservationInfoRepository.save(info)).thenReturn(null);
        when(mapper.toDto(null)).thenReturn(null);

        assertThrows(ReservationInfoServiceException.class, () -> reservationInfoService.create(infoDto));
        verify(reservationInfoRepository, times(1)).save(any(ReservationInfo.class));
        verify(mapper, times(1)).toEntity(any(ReservationInfoDto.class));
    }

    @Test
    void updateReservationInfoPositive() {
        info = EntityTest.getExpectedReservationInfoWithId();
        infoDto = DtoTest.getExpectedReservationInfoWithId();
        mockMapperToEntity();

        when(reservationInfoRepository.save(info)).thenReturn(info);
        mockMapperToDto();

        info.setCheckOut(LocalDate.parse("2022-11-27"));
        info.setNights(3L);
        infoDto.setCheckOut(LocalDate.parse("2022-11-27"));
        infoDto.setNights(3L);

        ReservationInfoDto actual = reservationInfoService.update(infoDto);
        assertEquals(infoDto, actual);
        verify(reservationInfoRepository, times(1)).save(any(ReservationInfo.class));
        verify(mapper, times(1)).toEntity(any(ReservationInfoDto.class));
        verify(mapper, times(1)).toDto(any(ReservationInfo.class));
    }

    @Test
    void updateReservationInfoReservationInfoServiceException() {
        mockMapperToEntity();
        when(reservationInfoRepository.save(info)).thenReturn(null);
        when(mapper.toDto(null)).thenReturn(null);

        info.setCheckOut(LocalDate.parse("2022-11-27"));
        info.setNights(3L);
        infoDto.setCheckOut(LocalDate.parse("2022-11-27"));
        infoDto.setNights(3L);

        assertThrows(ReservationInfoServiceException.class, () -> reservationInfoService.update(infoDto));
        verify(reservationInfoRepository, times(1)).save(any(ReservationInfo.class));
        verify(mapper, times(1)).toEntity(any(ReservationInfoDto.class));
    }

    @Test
    void deleteReservationInfoPositive() {
        info = EntityTest.getExpectedReservationInfoWithId();
        infoDto = DtoTest.getExpectedReservationInfoWithId();

        mockMapperToEntity();
        doNothing().when(reservationInfoRepository).delete(info);

        reservationInfoService.delete(infoDto);
        verify(reservationInfoRepository, times(1)).delete(info);
        verify(mapper, times(1)).toEntity(any(ReservationInfoDto.class));
    }

    @Test
    void deleteReservationInfoReservationInfoDeleteException() {
        info = EntityTest.getExpectedReservationInfoWithId();
        infoDto = DtoTest.getExpectedReservationInfoWithId();

        when(reservationInfoRepository.existsById(infoDto.getId())).thenReturn(true);
        assertThrows(ReservationInfoDeleteException.class, () -> reservationInfoService.delete(infoDto));
        verify(reservationInfoRepository, never()).delete(info);
    }
}