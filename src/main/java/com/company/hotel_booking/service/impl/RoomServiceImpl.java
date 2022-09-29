package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.dao.api.IRoomDao;
import com.company.hotel_booking.dao.entity.Room;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.service.api.IRoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.List;

/**
 * Class object RoomDTO with implementation of CRUD operation operations
 */
@Log4j2
public class RoomServiceImpl implements IRoomService {

    private final IRoomDao roomDao;

    public RoomServiceImpl(IRoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public RoomDto findById(Long id) {
        log.debug("Calling a service method findById. RoomDto id = {}", id);
        Room room = roomDao.findById(id);
        if (room == null) {
            log.error("SQLUserService findById error. No room with id = {}", id);
            throw new ServiceException(MessageManger.getMessage("msg.error.find") + id);
        }
        return toDto(room);
    }

    public List<RoomDto> findAll() {
        log.debug("Calling a service method findAll");
        return roomDao.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public RoomDto create(RoomDto roomDto) {
        log.debug("Calling a service method create. RoomDto = {}", roomDto);
        Room existing = roomDao.findRoomByNumber(roomDto.getNumber());
        if (existing != null) {
            log.error("Room with number = {} already exists", roomDto.getNumber());
            throw new ServiceException(MessageManger.getMessage("msg.error.exists"));
        }
        return toDto(roomDao.save(toEntity(roomDto)));
    }

    @Override
    public RoomDto update(RoomDto roomDto) {
        log.debug("Calling a service method update. RoomDto = {}", roomDto);
        Room existing = roomDao.findRoomByNumber((roomDto.getNumber()));
        if (existing != null && !existing.getId().equals(roomDto.getId())) {
            log.error("Room with number = {} already exists", roomDto.getNumber());
            throw new ServiceException(MessageManger.getMessage("msg.error.exists"));
        }
        return toDto(roomDao.update(toEntity(roomDto)));
    }

    @Override
    public void delete(Long id) {
        log.debug("Calling a service method delete. RoomDto id = {}", id);
        roomDao.delete(id);
        if (!roomDao.delete(id)) {
            log.error("SQLRoomService deleted error. Failed to delete room with id = {}", id);
            throw new ServiceException(MessageManger.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<RoomDto> findAllPages(Paging paging) {
        log.debug("Calling a service method findAllPages");
        return roomDao.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(this::toDto)
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
                .map(this::toDto)
                .toList();
    }

    /**
     * Method transforms object Room into object RoomDto
     *
     * @param entity object Room
     * @return object RoomDto
     */
    private RoomDto toDto(Room entity) {
        log.debug("Calling a service method toDto. Room = {}", entity);
        RoomDto dto = new RoomDto();
        try {
            dto.setId(entity.getId());
            dto.setType(RoomDto.RoomTypeDto.valueOf(entity.getType().toString()));
            dto.setPrice(entity.getPrice());
            dto.setStatus(RoomDto.RoomStatusDto.valueOf(entity.getStatus().toString()));
            dto.setCapacity(RoomDto.CapacityDto.valueOf(entity.getCapacity().toString()));
            dto.setNumber(entity.getNumber());
        } catch (NullPointerException e) {
            log.error("This room is not in the catalog.");
            throw new ServiceException(MessageManger.getMessage("msg.error.find"));
        }
        return dto;
    }

    /**
     * Method transforms object RoomDto into object Room
     *
     * @param dto RoomDto
     * @return object Room
     */
    private Room toEntity(RoomDto dto) {
        log.debug("Calling a service method toEntity. RoomDto = {}", dto);
        Room entity = new Room();
        entity.setId(dto.getId());
        entity.setType(Room.RoomType.valueOf(dto.getType().toString()));
        entity.setPrice(dto.getPrice());
        entity.setStatus(Room.RoomStatus.valueOf(dto.getStatus().toString()));
        entity.setCapacity(Room.Capacity.valueOf(dto.getCapacity().toString()));
        entity.setNumber(dto.getNumber());
        return entity;
    }
}