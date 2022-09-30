package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.dao.api.IReservationInfoDao;
import com.company.hotel_booking.dao.entity.ReservationInfo;
import com.company.hotel_booking.dao.entity.Room;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.service.api.IReservationInfoService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Class object ReservationInfoDTO with implementation of CRUD operation operations
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ReservationInfoServiceImpl implements IReservationInfoService {
    private final IReservationInfoDao reservationInfoDao;

    @Override
    public ReservationInfoDto findById(Long id) {
        log.debug("Calling a service method findById. ReservationInfoDto id = {}", id);
        ReservationInfo reservationInfo = reservationInfoDao.findById(id);
        if (reservationInfo == null) {
            log.error("SQLUserService findById error. No with id = {}", id);
            throw new ServiceException(MessageManger.getMessage("msg.error.find") + id);
        }
        return toDto(reservationInfo);
    }

    @Override
    public List<ReservationInfoDto> findAll() {
        log.debug("Calling a service method findAll");
        return reservationInfoDao.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ReservationInfoDto create(ReservationInfoDto entity) {
        log.debug("Calling a service method reate. ReservationInfo = {}", entity);
        return toDto(reservationInfoDao.save(toEntity(entity)));
    }

    @Override
    public List<ReservationInfoDto> processBookingInfo(Map<Long, Long> booking, LocalDate checkIn,
                                                       LocalDate checkOut, ReservationDto reservation) {
        log.debug("Calling a service method processBookingInfo");
        return reservationInfoDao.processBookingInfo(booking, checkIn, checkOut, reservation).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ReservationInfoDto update(ReservationInfoDto entity) {
        log.debug("Calling a service method update. ReservationInfo = {}", entity);
        return toDto(reservationInfoDao.update(toEntity(entity)));
    }

    @Override
    public void delete(Long id) {
        log.debug("Calling a service method delete. ReservationInfo id = {}", id);
        reservationInfoDao.delete(id);
        if (!reservationInfoDao.delete(id)) {
            log.error("SQLReservationService deleted error. Failed to delete reservation info with id = {}", id);
            throw new ServiceException("Failed to delete reservation info with id " + id);
        }
    }

    @Override
    public List<ReservationInfoDto> findAllPages(Paging paging) {
        log.debug("Calling a service method indAllPages");
        return reservationInfoDao.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public long countRow() {
        log.debug("Calling a service method countRow");
        return reservationInfoDao.countRow();
    }

    /**
     * Method transforms object ReservationInfo into objectReservationInfoDto
     *
     * @param entity ReservationInfo
     * @return object RReservationInfoDto
     */
    private ReservationInfoDto toDto(ReservationInfo entity) {
        log.debug("Calling a service method toDto. ReservationInfo = {}", entity);
        ReservationInfoDto dto = new ReservationInfoDto();
        try {
            dto.setId(entity.getId());
            dto.setReservationId(entity.getReservationId());
            dto.setRoom(getRoomDto(entity));
            dto.setCheckIn(entity.getCheckIn());
            dto.setCheckOut(entity.getCheckOut());
            dto.setNights(entity.getNights());
            dto.setRoomPrice(entity.getRoomPrice());
        } catch (NullPointerException e) {
            log.error("This reservation info is not in the catalog.");
            throw new ServiceException(MessageManger.getMessage("msg.empty"));
        }
        return dto;
    }

    /**
     * Method transforms object Room into object RoomDto
     *
     * @param entity ReservationInfo
     * @return object RoomDto
     */
    private RoomDto getRoomDto(ReservationInfo entity) {
        log.debug("Calling a service method getRoomDto. ReservationInfo = {}", entity);
        RoomDto dto = new RoomDto();
        try {
            dto.setId(entity.getRoom().getId());
            dto.setNumber((entity.getRoom().getNumber()));
            dto.setType(RoomDto.RoomTypeDto.valueOf(entity.getRoom().getType().toString()));
            dto.setCapacity(RoomDto.CapacityDto.valueOf(entity.getRoom().getCapacity().toString()));
            dto.setPrice(entity.getRoom().getPrice());
            dto.setStatus(RoomDto.RoomStatusDto.valueOf(entity.getRoom().getStatus().toString()));
        } catch (NullPointerException e) {
            log.error("This room is not in the catalog.");
            throw new ServiceException(MessageManger.getMessage("msg.empty"));
        }
        return dto;
    }

    /**
     * Method transforms object ReservationInfoDto into object ReservationInfo
     *
     * @param dto ReservationInfoDto
     * @return object ReservationInfo
     */
    private ReservationInfo toEntity(ReservationInfoDto dto) {
        log.debug("Calling a service method toEntity. ReservationInfoDto = {}", dto);
        ReservationInfo entity = new ReservationInfo();
        try {
            entity.setId(dto.getId());
            entity.setReservationId(dto.getReservationId());
            entity.setRoom(getRoom(dto));
            entity.setCheckIn(dto.getCheckIn());
            entity.setCheckOut(dto.getCheckOut());
            entity.setNights(dto.getNights());
            entity.setRoomPrice(dto.getRoomPrice());
        } catch (NullPointerException e) {
            log.error("This reservation info is not in the catalog.");
            throw new ServiceException(MessageManger.getMessage("msg.empty"));
        }
        return entity;
    }

    /**
     * Method transforms object RoomDto into object Room
     *
     * @param dto ReservationInfoDto
     * @return object Room
     */
    private Room getRoom(ReservationInfoDto dto) {
        log.debug("Calling a service method getRoom. ReservationInfoDto = {}", dto);
        Room entity = new Room();
        try {
            entity.setId(dto.getRoom().getId());
            entity.setNumber((dto.getRoom().getNumber()));
            entity.setType(Room.RoomType.valueOf(dto.getRoom().getType().toString()));
            entity.setCapacity(Room.Capacity.valueOf(dto.getRoom().getCapacity().toString()));
            entity.setPrice(dto.getRoom().getPrice());
            entity.setStatus(Room.RoomStatus.valueOf(dto.getRoom().getStatus().toString()));
        } catch (NullPointerException e) {
            log.error("This room is not in the catalog.");
            throw new ServiceException(MessageManger.getMessage("msg.empty"));
        }
        return entity;
    }
}