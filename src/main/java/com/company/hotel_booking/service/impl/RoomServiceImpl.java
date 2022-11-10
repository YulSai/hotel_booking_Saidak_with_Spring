package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.data.repository.RoomRepository;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.mapper.RoomMapper;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.utils.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.utils.exceptions.rooms.RoomAlreadyExistsException;
import com.company.hotel_booking.utils.exceptions.rooms.RoomDeleteException;
import com.company.hotel_booking.utils.exceptions.rooms.RoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    private final MessageSource messageSource;

    @Override
    @LogInvocationServer
    @NotFoundEx
    public RoomDto findById(Long id) {
        return mapper.toDto(roomRepository.findById(id).orElseThrow(
                () -> new RoomNotFoundException(messageSource.getMessage("msg.room.error.find.by.id", null,
                        LocaleContextHolder.getLocale()))));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public RoomDto create(RoomDto roomDto) {
        if (roomRepository.findByNumber(roomDto.getNumber()).isPresent()) {
            throw new RoomAlreadyExistsException(messageSource.getMessage("msg.room.error.create.exists", null,
                    LocaleContextHolder.getLocale()));
        }
        return mapper.toDto(roomRepository.save(mapper.toEntity(roomDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public RoomDto update(RoomDto roomDto) {
        Room existing = roomRepository.findByNumber((roomDto.getNumber())).get();
        if (existing != null && !existing.getId().equals(roomDto.getId())) {
            throw new RoomAlreadyExistsException(messageSource.getMessage("msg.room.error.update.exists", null,
                    LocaleContextHolder.getLocale()));
        }
        return mapper.toDto(roomRepository.save(mapper.toEntity(roomDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(RoomDto roomDto) {
        roomRepository.delete(mapper.toEntity(roomDto));
        if (roomRepository.existsById(roomDto.getId())) {
            throw new RoomDeleteException(messageSource.getMessage("msg.room.error.delete", null,
                    LocaleContextHolder.getLocale()));
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