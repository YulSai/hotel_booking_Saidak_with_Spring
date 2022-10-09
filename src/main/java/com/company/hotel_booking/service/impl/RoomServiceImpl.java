package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.data.dao.api.IRoomDao;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.data.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.IRoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Class object RoomDTO with implementation of CRUD operation operations
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private final IRoomDao roomDao;
    private final ObjectMapper mapper;

    @Override
    public RoomDto findById(Long id) {
        log.debug("Calling a service method findById. RoomDto id = {}", id);
        Room room = roomDao.findById(id);
        if (room == null) {
            log.error("SQLUserService findById error. No room with id = {}", id);
            throw new ServiceException(MessageManager.getMessage("msg.error.find") + id);
        }
        return mapper.toDto(room);
    }

    public List<RoomDto> findAll() {
        log.debug("Calling a service method findAll");
        return roomDao.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public RoomDto create(RoomDto roomDto) {
        log.debug("Calling a service method create. RoomDto = {}", roomDto);
        Room existing = roomDao.findRoomByNumber(roomDto.getNumber());
        if (existing != null) {
            log.error("Room with number = {} already exists", roomDto.getNumber());
            throw new ServiceException(MessageManager.getMessage("msg.error.exists"));
        }
        return mapper.toDto(roomDao.save(mapper.toEntity(roomDto)));
    }

    @Override
    public RoomDto update(RoomDto roomDto) {
        log.debug("Calling a service method update. RoomDto = {}", roomDto);
        Room existing = roomDao.findRoomByNumber((roomDto.getNumber()));
        if (existing != null && !existing.getId().equals(roomDto.getId())) {
            log.error("Room with number = {} already exists", roomDto.getNumber());
            throw new ServiceException(MessageManager.getMessage("msg.error.exists"));
        }
        return mapper.toDto(roomDao.update(mapper.toEntity(roomDto)));
    }

    @Override
    public void delete(Long id) {
        log.debug("Calling a service method delete. RoomDto id = {}", id);
        roomDao.delete(id);
        if (!roomDao.delete(id)) {
            log.error("SQLRoomService deleted error. Failed to delete room with id = {}", id);
            throw new ServiceException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<RoomDto> findAllPages(Paging paging) {
        log.debug("Calling a service method findAllPages");
        return roomDao.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public long countRow() {
        log.debug("Calling a service method countRow");
        return roomDao.countRow();
    }

    @Override
    public List<RoomDto> findAvailableRooms(LocalDate check_in, LocalDate check_out, String type, String capacity) {
        log.debug("Calling a service method findAvailableRooms");
        return roomDao.findAvailableRooms(check_in, check_out, type, capacity).stream()
                .map(mapper::toDto)
                .toList();
    }
}