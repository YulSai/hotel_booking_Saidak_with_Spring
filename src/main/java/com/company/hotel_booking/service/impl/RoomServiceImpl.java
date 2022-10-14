package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.aspects.logging.LogInvocationServer;
import com.company.hotel_booking.aspects.logging.ServiceEx;
import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.data.repository.api.RoomRepository;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.service.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Class object RoomDTO with implementation of CRUD operation operations
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ObjectMapper mapper;

    @Override
    @LogInvocationServer
    @ServiceEx
    public RoomDto findById(Long id) {
        Room room = roomRepository.findById(id);
        if (room == null) {
            throw new ServiceException(MessageManager.getMessage("msg.error.find") + id);
        }
        return mapper.toDto(room);
    }

    @Override
    @LogInvocationServer
    public List<RoomDto> findAll() {
        return roomRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public RoomDto create(RoomDto roomDto) {
        Room existing = roomRepository.findRoomByNumber(roomDto.getNumber());
        if (existing != null) {
            throw new ServiceException(MessageManager.getMessage("msg.error.exists"));
        }
        return mapper.toDto(roomRepository.create(mapper.toEntity(roomDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public RoomDto update(RoomDto roomDto) {
        Room existing = roomRepository.findRoomByNumber((roomDto.getNumber()));
        if (existing != null && !existing.getId().equals(roomDto.getId())) {
            throw new ServiceException(MessageManager.getMessage("msg.error.exists"));
        }
        return mapper.toDto(roomRepository.update(mapper.toEntity(roomDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(Long id) {
        roomRepository.delete(id);
        if (roomRepository.delete(id) != 1) {
            throw new ServiceException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    @LogInvocationServer
    public List<RoomDto> findAllPages(Paging paging) {
        return roomRepository.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @LogInvocationServer
    public long countRow() {
        return roomRepository.countRow();
    }

    @Override
    @LogInvocationServer
    public List<RoomDto> findAvailableRooms(LocalDate check_in, LocalDate check_out, String type, String capacity) {
        return roomRepository.findAvailableRooms(check_in, check_out, type, capacity).stream()
                .map(mapper::toDto)
                .toList();
    }
}