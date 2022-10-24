package com.company.hotel_booking.service.mapper;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import com.company.hotel_booking.utils.managers.MessageManager;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for converting dto to entity
 */
@Component
@RequiredArgsConstructor
public class ObjectMapper {

    private final MessageManager messageManager;

    /**
     * Method transforms object User into object UserDto
     *
     * @param entity object User
     * @return object UserDto
     */
    @LogInvocationServer
    @ServiceEx
    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        try {
            dto.setId(entity.getId());
            dto.setFirstName(entity.getFirstName());
            dto.setLastName(entity.getLastName());
            dto.setEmail(entity.getEmail());
            dto.setPassword(entity.getPassword());
            dto.setPhoneNumber(entity.getPhoneNumber());
            dto.setRole(UserDto.RoleDto.valueOf((entity.getRole().toString())));
            dto.setAvatar(entity.getAvatar());
        } catch (NullPointerException e) {
            throw new ServiceException(messageManager.getMessage("msg.error.find"));
        }
        return dto;
    }

    /**
     * Method transforms object UserDto into object User
     *
     * @param dto UserDto
     * @return object User
     */
    @LogInvocationServer
    @ServiceEx
    public User toEntity(UserDto dto) {
        User entity = new User();
        try {
            entity.setId(dto.getId());
            entity.setFirstName(dto.getFirstName());
            entity.setLastName(dto.getLastName());
            entity.setEmail(dto.getEmail());
            entity.setPassword(dto.getPassword());
            entity.setPhoneNumber(dto.getPhoneNumber());
            entity.setRole(User.Role.valueOf(dto.getRole().toString()));
            entity.setAvatar(dto.getAvatar());
        } catch (NullPointerException e) {
            throw new ServiceException(messageManager.getMessage("msg.error.find"));
        }
        return entity;
    }

    /**
     * Method transforms object Room into object RoomDto
     *
     * @param entity object Room
     * @return object RoomDto
     */
    @LogInvocationServer
    @ServiceEx
    public RoomDto toDto(Room entity) {
        RoomDto dto = new RoomDto();
        try {
            dto.setId(entity.getId());
            dto.setType(RoomDto.RoomTypeDto.valueOf(entity.getType().toString()));
            dto.setPrice(entity.getPrice());
            dto.setStatus(RoomDto.RoomStatusDto.valueOf(entity.getStatus().toString()));
            dto.setCapacity(RoomDto.CapacityDto.valueOf(entity.getCapacity().toString()));
            dto.setNumber(entity.getNumber());
        } catch (NullPointerException e) {
            throw new ServiceException(messageManager.getMessage("msg.error.find"));
        }
        return dto;
    }

    /**
     * Method transforms object RoomDto into object Room
     *
     * @param dto RoomDto
     * @return object Room
     */
    @LogInvocationServer
    @ServiceEx
    public Room toEntity(RoomDto dto) {
        Room entity = new Room();
        try {
            entity.setId(dto.getId());
            entity.setType(Room.RoomType.valueOf(dto.getType().toString()));
            entity.setPrice(dto.getPrice());
            entity.setStatus(Room.RoomStatus.valueOf(dto.getStatus().toString()));
            entity.setCapacity(Room.Capacity.valueOf(dto.getCapacity().toString()));
            entity.setNumber(dto.getNumber());
        } catch (NullPointerException e) {
            throw new ServiceException(messageManager.getMessage("msg.error.find"));
        }
        return entity;
    }

    /**
     * Method transforms object Reservation into object ReservationDto
     *
     * @param entity Reservation
     * @return object ReservationDto
     */
    @LogInvocationServer
    @ServiceEx
    public ReservationDto toDto(Reservation entity) {
        ReservationDto dto = new ReservationDto();
        try {
            dto.setId(entity.getId());
            dto.setUser(toDto(entity.getUser()));
            dto.setTotalCost(entity.getTotalCost());
            dto.setStatus(ReservationDto.StatusDto.valueOf(entity.getStatus().toString()));
            List<ReservationInfoDto> reservationInfoDto = new ArrayList<>();
            List<ReservationInfo> reservationInfo = entity.getDetails();
            for (ReservationInfo info : reservationInfo) {
                ReservationInfoDto resDto = new ReservationInfoDto();
                resDto.setId(info.getId());
                resDto.setReservationDto(dto);
                resDto.setRoom(toDto(info.getRoom()));
                resDto.setCheckIn(info.getCheckIn());
                resDto.setCheckOut(info.getCheckOut());
                resDto.setNights(info.getNights());
                resDto.setRoomPrice(info.getRoomPrice());
                reservationInfoDto.add(resDto);
            }
            dto.setDetails(reservationInfoDto);
        } catch (NullPointerException e) {
            throw new ServiceException(messageManager.getMessage("msg.empty"));
        }
        return dto;
    }

    /**
     * Method transforms object ReservationDto into object Reservation
     *
     * @param dto ReservationDto
     * @return object Reservation
     */
    @LogInvocationServer
    @ServiceEx
    public Reservation toEntity(ReservationDto dto) {
        Reservation entity = new Reservation();
        try {
            entity.setId(dto.getId());
            entity.setUser(toEntity(dto.getUser()));
            entity.setTotalCost(dto.getTotalCost());
            entity.setStatus(Reservation.Status.valueOf(dto.getStatus().toString()));
            List<ReservationInfoDto> reservationInfoDto = dto.getDetails();
            List<ReservationInfo> reservationInfo = new ArrayList<>();
            for (ReservationInfoDto infoDto : reservationInfoDto) {
                ReservationInfo info = new ReservationInfo();
                info.setId(infoDto.getId());
                info.setReservation(entity);
                info.setRoom(toEntity(infoDto.getRoom()));
                info.setCheckIn(infoDto.getCheckIn());
                info.setCheckOut(infoDto.getCheckOut());
                info.setNights(infoDto.getNights());
                info.setRoomPrice(infoDto.getRoomPrice());
                reservationInfo.add(info);
            }
            entity.setDetails(reservationInfo);
        } catch (NullPointerException e) {
            throw new ServiceException(messageManager.getMessage("msg.empty"));
        }
        return entity;
    }

    /**
     * Method transforms object ReservationInfo into objectReservationInfoDto
     *
     * @param entity ReservationInfo
     * @return object RReservationInfoDto
     */
    @LogInvocationServer
    @ServiceEx
    public ReservationInfoDto toDto(ReservationInfo entity) {
        ReservationInfoDto dto = new ReservationInfoDto();
        try {
            dto.setId(entity.getId());
            dto.setReservationDto(toDto(entity.getReservation()));
            dto.setRoom(toDto(entity.getRoom()));
            dto.setCheckIn(entity.getCheckIn());
            dto.setCheckOut(entity.getCheckOut());
            dto.setNights(entity.getNights());
            dto.setRoomPrice(entity.getRoomPrice());
        } catch (NullPointerException e) {
            throw new ServiceException(messageManager.getMessage("msg.empty"));
        }
        return dto;
    }

    /**
     * Method transforms object ReservationInfoDto into object ReservationInfo
     *
     * @param dto ReservationInfoDto
     * @return object ReservationInfo
     */
    @LogInvocationServer
    @ServiceEx
    public ReservationInfo toEntity(ReservationInfoDto dto) {
        ReservationInfo entity = new ReservationInfo();
        try {
            entity.setId(dto.getId());
            entity.setReservation(toEntity(dto.getReservationDto()));
            entity.setRoom(toEntity(dto.getRoom()));
            entity.setCheckIn(dto.getCheckIn());
            entity.setCheckOut(dto.getCheckOut());
            entity.setNights(dto.getNights());
            entity.setRoomPrice(dto.getRoomPrice());
        } catch (NullPointerException e) {
            throw new ServiceException(messageManager.getMessage("msg.empty"));
        }
        return entity;
    }
}
