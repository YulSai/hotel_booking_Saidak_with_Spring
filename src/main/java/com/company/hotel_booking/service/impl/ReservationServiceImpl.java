package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.dao.api.IReservationDao;
import com.company.hotel_booking.dao.api.IRoomDao;
import com.company.hotel_booking.dao.entity.Reservation;
import com.company.hotel_booking.dao.entity.ReservationInfo;
import com.company.hotel_booking.dao.entity.Room;
import com.company.hotel_booking.dao.entity.User;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.service.api.IReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Class object ReservationDTO with implementation of CRUD operation operations
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationDao reservationDao;
    private final IRoomDao roomDao;

    @Override
    public ReservationDto findById(Long id) {
        log.debug("Calling a service method findById. Reservation id = {}", id);
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            log.error("SQLReservationService findById error. id = {}", id);
            throw new ServiceException(MessageManger.getMessage("msg.empty") + id);
        }
        return toDto(reservation);
    }

    public List<ReservationDto> findAll() {
        log.debug("Calling a service method findAll");
        return reservationDao.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ReservationDto create(ReservationDto entity) {
        log.debug("Calling a service method create. Reservation = {}", entity);
        entity.setStatus(ReservationDto.StatusDto.CONFIRMED);

        return toDto(reservationDao.save(toEntity(entity)));
    }

    @Override
    public ReservationDto processBooking(Map<Long, Long> booking, UserDto user, LocalDate checkIn,
                                         LocalDate checkOut) {
        ReservationDto reservation = new ReservationDto();
        reservation.setUser(user);
        reservation.setStatus(ReservationDto.StatusDto.IN_PROGRESS);
        List<ReservationInfoDto> details = new ArrayList<>();
        booking.forEach((roomId, quantity) -> {
            ReservationInfoDto info = new ReservationInfoDto();
            RoomDto room = getRoomDto(roomId);
            info.setRoom(room);
            info.setCheckIn(checkIn);
            info.setCheckOut(checkOut);
            info.setNights(ChronoUnit.DAYS.between(checkIn, checkOut));
            info.setRoomPrice(room.getPrice());
            details.add(info);
        });
        reservation.setDetails(details);
        BigDecimal totalCost = calculatePrice(details);
        reservation.setTotalCost(totalCost);
        return reservation;
    }

    private BigDecimal calculatePrice(List<ReservationInfoDto> details) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (ReservationInfoDto detail : details) {
            BigDecimal roomPrice = detail.getRoomPrice();
            Long nights = ChronoUnit.DAYS.between(detail.getCheckIn(), detail.getCheckOut());
            totalCost = totalCost.add(roomPrice.multiply(BigDecimal.valueOf(nights)));
        }
        return totalCost;
    }

    @Override
    public ReservationDto update(ReservationDto entity) {
        log.debug("Calling a service method update. Reservation = {}", entity);
        return toDto(reservationDao.update(toEntity(entity)));
    }

    @Override
    public void delete(Long id) {
        log.debug("Calling a service method delete. Reservation id = {}", id);
        reservationDao.delete(id);
        if (!reservationDao.delete(id)) {
            log.error("SQLReservationService deleted error. Failed to delete reservation with id = {}", id);
            throw new ServiceException(MessageManger.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<ReservationDto> findAllPages(Paging paging) {
        log.debug("Calling a service method findAllPages");
        return reservationDao.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<ReservationDto> findAllPagesByUsers(Paging paging, Long id) {
        log.debug("Calling a service method findAllPagesByUsers");
        return reservationDao.findAllPagesByUsers(paging.getLimit(), paging.getOffset(), id).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public long countRow() {
        log.debug("Calling a service method countRow");
        return reservationDao.countRow();
    }

    @Override
    public List<ReservationDto> findAllByUsers(Long id) {
        log.debug("Calling a service method findAllPagesByUsers");
        return reservationDao.findAllByUsers(id).stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Method transforms object Reservation into object ReservationDto
     *
     * @param entity Reservation
     * @return object ReservationDto
     */
    private ReservationDto toDto(Reservation entity) {
        log.debug("Calling a service method toDto. Reservation = {}", entity);
        ReservationDto dto = new ReservationDto();
        try {
            dto.setId(entity.getId());
            dto.setUser(getUserDto(entity));
            dto.setTotalCost(entity.getTotalCost());
            dto.setStatus(ReservationDto.StatusDto.valueOf(entity.getStatus().toString()));
            List<ReservationInfoDto> reservationInfoDto = new ArrayList<>();
            List<ReservationInfo> reservationInfo = entity.getDetails();
            for (ReservationInfo info : reservationInfo) {
                ReservationInfoDto resDto = getReservationInfoDto(info);
                reservationInfoDto.add(resDto);
            }
            dto.setDetails(reservationInfoDto);
        } catch (NullPointerException e) {
            log.error("This reservation is not in the catalog.");
            throw new ServiceException(MessageManger.getMessage("msg.empty"));
        }
        return dto;
    }

    private ReservationInfoDto getReservationInfoDto(ReservationInfo info) {
        log.debug("Calling a service method getReservationInfoDto. ReservationInfo = {}", info);
        ReservationInfoDto resDto = new ReservationInfoDto();
        resDto.setId(info.getId());
        resDto.setReservationId(info.getReservationId());
        resDto.setRoom(getRoomDto(info.getRoom().getId()));
        resDto.setCheckIn(info.getCheckIn());
        resDto.setCheckOut(info.getCheckOut());
        resDto.setNights(info.getNights());
        resDto.setRoomPrice(info.getRoomPrice());
        return resDto;
    }

    /**
     * Method transforms object User into object UserDto
     *
     * @param entity Reservation
     * @return object UserDto
     */
    private UserDto getUserDto(Reservation entity) {
        log.debug("Calling a service method getUserDto. Reservation = {}", entity);
        UserDto dto = new UserDto();
        try {
            dto.setId(entity.getUser().getId());
            dto.setFirstName(entity.getUser().getFirstName());
            dto.setLastName(entity.getUser().getLastName());
            dto.setEmail(entity.getUser().getEmail());
            dto.setPassword(entity.getUser().getPassword());
            dto.setPhoneNumber(entity.getUser().getPhoneNumber());
            dto.setRole(UserDto.RoleDto.valueOf(entity.getUser().getRole().toString()));
            dto.setAvatar(entity.getUser().getAvatar());
        } catch (NullPointerException e) {
            log.error("This user is not in the catalog.");
            throw new ServiceException(MessageManger.getMessage("msg.empty"));
        }
        return dto;
    }

    /**
     * Method transforms object ReservationDto into object Reservation
     *
     * @param dto ReservationDto
     * @return object Reservation
     */
    private Reservation toEntity(ReservationDto dto) {
        log.debug("Calling a service method toEntity. ReservationDto = {}", dto);
        Reservation entity = new Reservation();
        try {
            entity.setId(dto.getId());
            entity.setUser(getUser(dto));
            entity.setTotalCost(dto.getTotalCost());
            entity.setStatus(Reservation.Status.valueOf(dto.getStatus().toString()));
        } catch (NullPointerException e) {
            log.error("This reservation is not in the catalog.");
            throw new ServiceException(MessageManger.getMessage("msg.empty"));
        }
        return entity;
    }

    /**
     * Method transforms object UserDto into object User
     *
     * @param dto ReservationDto
     * @return object User
     */
    private User getUser(ReservationDto dto) {
        log.debug("Calling a service method getUser. ReservationDto = {}", dto);
        User entity = new User();
        try {
            entity.setId(dto.getUser().getId());
            entity.setFirstName(dto.getUser().getFirstName());
            entity.setLastName(dto.getUser().getLastName());
            entity.setEmail(dto.getUser().getEmail());
            entity.setPassword(dto.getUser().getPassword());
            entity.setPhoneNumber(dto.getUser().getPhoneNumber());
            entity.setRole(User.Role.valueOf(dto.getUser().getRole().toString()));
            entity.setAvatar(dto.getUser().getAvatar());
        } catch (NullPointerException e) {
            log.error("This user is not in the catalog.");
            throw new ServiceException(MessageManger.getMessage("msg.empty"));
        }
        return entity;
    }

    /**
     * Method transforms object RoomDto into object Room
     *
     * @param roomId id Room
     * @return object RoomDto
     */
    private RoomDto getRoomDto(Long roomId) {
        log.debug("Calling a service method getRoomDto. Room id = {}", roomId);
        RoomDto room = new RoomDto();
        Room entity = roomDao.findById(roomId);
        room.setId(entity.getId());
        room.setType(RoomDto.RoomTypeDto.valueOf(entity.getType().toString()));
        room.setCapacity(RoomDto.CapacityDto.valueOf(entity.getCapacity().toString()));
        room.setStatus(RoomDto.RoomStatusDto.valueOf(entity.getStatus().toString()));
        room.setPrice(entity.getPrice());
        room.setNumber(entity.getNumber());
        return room;
    }
}