package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.data.repository.RoomRepository;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.mapper.RoomMapper;
import com.company.hotel_booking.utils.DtoTest;
import com.company.hotel_booking.utils.EntityTest;
import com.company.hotel_booking.utils.TestConstants;
import com.company.hotel_booking.utils.exceptions.rooms.RoomAlreadyExistsException;
import com.company.hotel_booking.utils.exceptions.rooms.RoomDeleteException;
import com.company.hotel_booking.utils.exceptions.rooms.RoomNotFoundException;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

/**
 * Class with tests for RoomServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {
    private RoomService roomService;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomMapper mapper;
    @Mock
    private MessageSource messageSource;

    private Room room;
    private RoomDto roomDto;

    @BeforeEach
    public void setup() {
        roomService = new RoomServiceImpl(roomRepository, mapper, messageSource);
        room = EntityTest.getExpectedRoomWithoutId();
        roomDto = DtoTest.getExpectedRoomWithoutId();
    }

    private void mockMapperToEntity() {
        when(mapper.toEntity(roomDto)).thenReturn(room);
    }

    private void mockMapperToDto() {
        when(mapper.toDto(room)).thenReturn(roomDto);
    }

    @Test
    void whenFindExitingRoomById_thenReturnRoom() {
        when(roomRepository.findById(TestConstants.ROOM_ID)).thenReturn(Optional.of(room));
        mockMapperToDto();

        RoomDto actual = roomService.findById(TestConstants.ROOM_ID);

        assertEquals(roomDto, actual);
        verify(roomRepository, times(1)).findById(TestConstants.ROOM_ID);
        verify(mapper, times(1)).toDto(any(Room.class));
    }

    @Test
    void whenFindNonExitingRoomById_thenThrowException() {
        when(roomRepository.findById(TestConstants.ROOM_ID)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.findById(TestConstants.ROOM_ID));
        verify(mapper, never()).toDto(any(Room.class));
    }

    @Test
    void whenFindAllRooms_thenReturnRooms() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Room> pageRoom = new PageImpl<>(new ArrayList<>());
        Page<RoomDto> pageRoomDto = new PageImpl<>(new ArrayList<>());

        when(roomRepository.findAll(pageable)).thenReturn(pageRoom);
        when(roomRepository.findAll(pageable).map(mapper::toDto)).thenReturn(pageRoomDto);

        Page<RoomDto> actual = roomService.findAllPages(pageable);
        assertNotNull(actual);
        verify(roomRepository, times(1)).findAll(pageable);
    }

    @Test
    void whenCreateNewRoom_thenReturnCreatedRoom() {
        mockMapperToEntity();
        when(roomRepository.findByNumber(room.getNumber())).thenReturn(Optional.empty());
        when(roomRepository.save(room)).thenReturn(room);
        mockMapperToDto();

        RoomDto actual = roomService.create(roomDto);

        assertEquals(roomDto, actual);
        verify(roomRepository, times(1)).save(any(Room.class));
        verify(mapper, times(1)).toEntity(any(RoomDto.class));
        verify(mapper, times(1)).toDto(any(Room.class));
    }

    @Test
    void whenCreateRoomWithExistingRoomNumber_thenThrowException() {
        when(roomRepository.findByNumber(room.getNumber())).thenReturn(Optional.ofNullable(room));

        assertThrows(RoomAlreadyExistsException.class, () -> roomService.create(roomDto));
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void whenUpdateRoom_thenReturnUpdatedRoom() {
        room = EntityTest.getExpectedRoomWithId();
        roomDto = DtoTest.getExpectedRoomWithId();
        mockMapperToEntity();

        when(roomRepository.findByNumber((roomDto.getNumber()))).thenReturn(Optional.ofNullable(room));
        when(roomRepository.save(room)).thenReturn(room);
        mockMapperToDto();

        room.setPrice(BigDecimal.valueOf(200));
        room.setStatus(Room.RoomStatus.valueOf("UNAVAILABLE"));
        roomDto.setPrice(BigDecimal.valueOf(200));
        roomDto.setStatus(RoomDto.RoomStatusDto.valueOf("UNAVAILABLE"));

        RoomDto actual = roomService.update(roomDto);
        assertEquals(roomDto, actual);
        verify(roomRepository, times(1)).save(any(Room.class));
        verify(mapper, times(1)).toEntity(any(RoomDto.class));
        verify(mapper, times(1)).toDto(any(Room.class));
    }

    @Test
    void whenUpdateRoomWithSameId_thenThrowException() {
        room = EntityTest.getExpectedRoomWithId();
        roomDto = DtoTest.getExpectedRoomWithId();
        Room existing = room;
        existing.setId(20L);

        when(roomRepository.findByNumber((roomDto.getNumber()))).thenReturn(Optional.of(existing));

        assertThrows(RoomAlreadyExistsException.class, () -> roomService.update(roomDto));
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void whenDeleteRoom_thenRoomIsDeleted() {
        room = EntityTest.getExpectedRoomWithId();
        roomDto = DtoTest.getExpectedRoomWithId();

        mockMapperToEntity();
        doNothing().when(roomRepository).delete(room);

        roomService.delete(roomDto);
        verify(roomRepository, times(1)).delete(room);
        verify(mapper, times(1)).toEntity(any(RoomDto.class));
    }

    @Test
    void whenDeleteRoom_thenThrowException() {
        room = EntityTest.getExpectedRoomWithId();
        roomDto = DtoTest.getExpectedRoomWithId();

        when(roomRepository.existsById(roomDto.getId())).thenReturn(true);
        assertThrows(RoomDeleteException.class, () -> roomService.delete(roomDto));
        verify(roomRepository, never()).delete(room);
    }

    @Test
    void whenFindAvailableRooms_thenReturnAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        List<RoomDto> roomDto = new ArrayList<>();

        Long typeId = room.getType().getId();
        Long capacityId = room.getCapacity().getId();
        LocalDate check_in = TestConstants.RESERVATION_INFO_CHECKIN;
        LocalDate check_out = TestConstants.RESERVATION_INFO_CHECKOUT;

        when(roomRepository.findAvailableRooms(typeId, capacityId, check_in, check_out, check_in, check_out))
                .thenReturn(rooms);
        when(roomRepository.findAvailableRooms(typeId, capacityId, check_in, check_out, check_in, check_out).stream()
                .map(mapper::toDto).toList()).thenReturn(roomDto);

        List<RoomDto> actual = roomService.findAvailableRooms(typeId, capacityId, check_in, check_out);
        assertNotNull(actual);
        verify(roomRepository, times(1)).findAvailableRooms(typeId, capacityId, check_in, check_out, check_in,
                check_out);
    }
}