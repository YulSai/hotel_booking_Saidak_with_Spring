package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.service.mapper.RoomMapper;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.data.repository.RoomRepository;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final RoomMapper mapper;

    @Override
    @LogInvocationServer
    @ServiceEx
    public RoomDto findById(Long id) {
        return mapper.toDto(roomRepository.findById(id).orElseThrow(
                () -> new ServiceException("msg.room.error.find.by.id")));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public RoomDto create(RoomDto roomDto) {
        if (roomRepository.findByNumber(roomDto.getNumber()).isPresent()) {
            throw new ServiceException("msg.room.error.create.exists");
        }
        return mapper.toDto(roomRepository.save(mapper.toEntity(roomDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public RoomDto update(RoomDto roomDto) {
        Room existing = roomRepository.findByNumber((roomDto.getNumber())).get();
        if (existing != null && !existing.getId().equals(roomDto.getId())) {
            throw new ServiceException("msg.room.error.update.exists");
        }
        return mapper.toDto(roomRepository.save(mapper.toEntity(roomDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(RoomDto roomDto) {
        roomRepository.delete(mapper.toEntity(roomDto));
        if (roomRepository.existsById(roomDto.getId())) {
            throw new ServiceException("msg.room.error.delete");
        }
    }

    @Override
    @LogInvocationServer
    public Page<RoomDto> findAllPages(Pageable pageable) {
        return roomRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    @LogInvocationServer
    public List<RoomDto> findAvailableRooms(Long typeId, Long capacityId, LocalDate check_in, LocalDate check_out) {
        return roomRepository.findAvailableRooms(typeId, capacityId, check_in, check_out, check_in, check_out).stream()
                .map(mapper::toDto)
                .toList();
    }
}