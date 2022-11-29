package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.data.repository.ReservationRepository;
import com.company.hotel_booking.data.repository.RoomRepository;
import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.service.mapper.ReservationMapper;
import com.company.hotel_booking.service.mapper.RoomMapper;
import com.company.hotel_booking.utils.DtoTest;
import com.company.hotel_booking.utils.EntityTest;
import com.company.hotel_booking.utils.TestConstants;
import com.company.hotel_booking.utils.exceptions.reservations.ReservationDeleteException;
import com.company.hotel_booking.utils.exceptions.reservations.ReservationNotFoundException;
import com.company.hotel_booking.utils.exceptions.reservations.ReservationServiceException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
class ReservationServiceImplTest {
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ReservationMapper mapper;
    @Mock
    private RoomMapper roomMapper;
    @Mock
    private MessageSource messageSource;

    private Reservation reservation;
    private ReservationDto reservationDto;

    @BeforeEach
    public void setup() {
        reservationService = new ReservationServiceImpl(reservationRepository, roomRepository, mapper, roomMapper,
                messageSource);
        reservation = EntityTest.getExpectedReservationWithoutId();
        reservationDto = DtoTest.getExpectedReservationWithoutId();
    }

    private void mockMapperToEntity() {
        when(mapper.toEntity(reservationDto)).thenReturn(reservation);
    }

    private void mockMapperToDto() {
        when(mapper.toDto(reservation)).thenReturn(reservationDto);
    }

    @Test
    void getReservationPositive() {
        when(reservationRepository.findById(TestConstants.RESERVATION_ID)).thenReturn(Optional.of(reservation));
        mockMapperToDto();

        ReservationDto actual = reservationService.findById(TestConstants.RESERVATION_ID);

        assertEquals(reservationDto, actual);
        verify(reservationRepository, times(1)).findById(TestConstants.RESERVATION_ID);
        verify(mapper, times(1)).toDto(any(Reservation.class));
    }

    @Test
    void getReservationNotFound() {
        when(reservationRepository.findById(TestConstants.RESERVATION_ID)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class,
                () -> reservationService.findById(TestConstants.RESERVATION_ID));
        verify(mapper, never()).toDto(any(Reservation.class));
    }

    @Test
    void getAllReservationPositive() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Reservation> pageReservation = new PageImpl<>(new ArrayList<>());
        Page<ReservationDto> pageReservationDto = new PageImpl<>(new ArrayList<>());

        when(reservationRepository.findAll(pageable)).thenReturn(pageReservation);
        when(reservationRepository.findAll(pageable).map(mapper::toDto)).thenReturn(pageReservationDto);

        Page<ReservationDto> actual = reservationService.findAllPages(pageable);
        assertNotNull(actual);
        verify(reservationRepository, times(1)).findAll(pageable);
    }

    @Test
    void createReservationPositive() {
        mockMapperToEntity();
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        mockMapperToDto();

        ReservationDto actual = reservationService.create(reservationDto);

        assertEquals(reservationDto, actual);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(mapper, times(1)).toEntity(any(ReservationDto.class));
        verify(mapper, times(1)).toDto(any(Reservation.class));
    }

    @Test
    void createReservationReservationServiceException() {
        mockMapperToEntity();
        when(reservationRepository.save(reservation)).thenReturn(null);
        when(mapper.toDto(null)).thenReturn(null);

        assertThrows(ReservationServiceException.class, () -> reservationService.create(reservationDto));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(mapper, times(1)).toEntity(any(ReservationDto.class));
    }

    @Test
    void updateReservationPositive() {
        reservation = EntityTest.getExpectedReservationWithId();
        reservationDto = DtoTest.getExpectedReservationWithId();
        mockMapperToEntity();

        when(reservationRepository.save(reservation)).thenReturn(reservation);
        mockMapperToDto();

        reservation.setStatus(Reservation.Status.valueOf("REJECTED"));
        reservationDto.setStatus(ReservationDto.StatusDto.valueOf("REJECTED"));

        ReservationDto actual = reservationService.update(reservationDto);
        assertEquals(reservationDto, actual);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(mapper, times(1)).toEntity(any(ReservationDto.class));
        verify(mapper, times(1)).toDto(any(Reservation.class));
    }

    @Test
    void updateReservationReservationServiceException() {
        mockMapperToEntity();
        when(reservationRepository.save(reservation)).thenReturn(null);
        when(mapper.toDto(null)).thenReturn(null);

        reservation.setStatus(Reservation.Status.valueOf("REJECTED"));
        reservationDto.setStatus(ReservationDto.StatusDto.valueOf("REJECTED"));

        assertThrows(ReservationServiceException.class, () -> reservationService.update(reservationDto));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(mapper, times(1)).toEntity(any(ReservationDto.class));
    }

    @Test
    void deleteReservationPositive() {
        reservation = EntityTest.getExpectedReservationWithId();
        reservationDto = DtoTest.getExpectedReservationWithId();

        mockMapperToEntity();
        doNothing().when(reservationRepository).delete(reservation);

        reservationService.delete(reservationDto);
        verify(reservationRepository, times(1)).delete(reservation);
        verify(mapper, times(1)).toEntity(any(ReservationDto.class));
    }

    @Test
    void deleteReservationReservationDeleteException() {
        reservation = EntityTest.getExpectedReservationWithId();
        reservationDto = DtoTest.getExpectedReservationWithId();

        when(reservationRepository.existsById(reservationDto.getId())).thenReturn(true);
        assertThrows(ReservationDeleteException.class, () -> reservationService.delete(reservationDto));
        verify(reservationRepository, never()).delete(reservation);
    }

    @Test
    void getAllReservationByUserPositive() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Reservation> pageReservation = new PageImpl<>(new ArrayList<>());
        Page<ReservationDto> pageReservationDto = new PageImpl<>(new ArrayList<>());

        when(reservationRepository.findAllByUserId(pageable, TestConstants.USER_ID)).thenReturn(pageReservation);
        when(reservationRepository.findAllByUserId(pageable, TestConstants.USER_ID).map(mapper::toDto)).thenReturn(
                pageReservationDto);

        Page<ReservationDto> actual = reservationService.findAllPagesByUsers(pageable, TestConstants.USER_ID);
        assertNotNull(actual);
        verify(reservationRepository, times(1)).findAllByUserId(pageable, TestConstants.USER_ID);
    }

    @Test
    void getAllByUserPositive() {
        List<Reservation> pageReservation = new ArrayList<>();
        List<ReservationDto> pageReservationDto = new ArrayList<>();

        when(reservationRepository.findByUserId(TestConstants.USER_ID)).thenReturn(pageReservation);
        when(reservationRepository.findByUserId(TestConstants.USER_ID).stream().map(mapper::toDto).toList()).thenReturn(
                pageReservationDto);

        List<ReservationDto> actual = reservationService.findAllByUsers(TestConstants.USER_ID);
        assertNotNull(actual);
        verify(reservationRepository, times(1)).findByUserId(TestConstants.USER_ID);
    }

    @Test
    void processReservationCreationPositive() {
        UserDto userDto = DtoTest.getExpectedUserWithId();
        Room room = reservation.getDetails().get(0).getRoom();
        RoomDto roomDto = reservationDto.getDetails().get(0).getRoom();
        LocalDate checkIn = reservation.getDetails().get(0).getCheckIn();
        LocalDate checkOut = reservation.getDetails().get(0).getCheckOut();

        Map<Long, Long> booking = new HashMap<>();
        booking.put(room.getId(), reservation.getDetails().get(0).getNights());

        when(roomMapper.toDto(roomRepository.findById(room.getId()).orElse(null))).thenReturn(roomDto);
        when(mapper.toDto(reservationRepository.save(reservation))).thenReturn(reservationDto);

        ReservationDto created = reservationService.processReservationCreation(booking, userDto, checkIn, checkOut);

        assertEquals(reservationDto, created);
        verify(roomRepository, times(2)).findById(room.getId());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }
}