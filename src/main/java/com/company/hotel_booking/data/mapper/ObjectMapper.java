package com.company.hotel_booking.data.mapper;

import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.data.repository.api.ReservationRepository;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class ObjectMapper {
    private final ReservationRepository reservationRepository;

    /**
     * Method transforms object User into object UserDto
     *
     * @param entity object User
     * @return object UserDto
     */
    public UserDto toDto(User entity) {
        log.debug("Calling a service method toDto. User = {}", entity);
        UserDto dto = new UserDto();
        try {
            dto.setId(entity.getId());
            dto.setFirstName(entity.getFirstName());
            dto.setLastName(entity.getLastName());
            dto.setEmail(entity.getEmail());
            dto.setPassword(entity.getPassword());
            dto.setPhoneNumber(entity.getPhoneNumber());
            dto.setRole(UserDto.RoleDto.valueOf(entity.getRole().toString()));
            dto.setAvatar(entity.getAvatar());
        } catch (NullPointerException e) {
            log.error("This user is not in the catalog");
            throw new ServiceException(MessageManager.getMessage("msg.error.find"));
        }
        return dto;
    }

    /**
     * Method transforms object UserDto into object User
     *
     * @param dto UserDto
     * @return object User
     */
    public User toEntity(UserDto dto) {
        log.debug("Calling a service method toEntity. UserDto = {}", dto);
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
            log.error("This user is not in the catalog");
            throw new ServiceException(MessageManager.getMessage("msg.error.find"));
        }
        return entity;
    }

    /**
     * Method transforms object Room into object RoomDto
     *
     * @param entity object Room
     * @return object RoomDto
     */
    public RoomDto toDto(Room entity) {
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
            throw new ServiceException(MessageManager.getMessage("msg.error.find"));
        }
        return dto;
    }

    /**
     * Method transforms object RoomDto into object Room
     *
     * @param dto RoomDto
     * @return object Room
     */
    public Room toEntity(RoomDto dto) {
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

    /**
     * Method transforms object Reservation into object ReservationDto
     *
     * @param entity Reservation
     * @return object ReservationDto
     */
    public ReservationDto toDto(Reservation entity) {
        log.debug("Calling a service method toDto. Reservation = {}", entity);
        ReservationDto dto = new ReservationDto();
        try {
            dto.setId(entity.getId());
            dto.setUser(toDto(entity.getUser()));
            dto.setTotalCost(entity.getTotalCost());
            dto.setStatus(ReservationDto.StatusDto.valueOf(entity.getStatus().toString()));
            dto.setDetails(entity.getDetails().stream().map(this::toDto).toList());
        } catch (NullPointerException e) {
            log.error("This reservation is not in the catalog.");
            throw new ServiceException(MessageManager.getMessage("msg.empty"));
        }
        return dto;
    }

    /**
     * Method transforms object ReservationDto into object Reservation
     *
     * @param dto ReservationDto
     * @return object Reservation
     */
    public Reservation toEntity(ReservationDto dto) {
        log.debug("Calling a service method toEntity. ReservationDto = {}", dto);
        Reservation entity = new Reservation();
        try {
            entity.setId(dto.getId());
            entity.setUser(toEntity(dto.getUser()));
            entity.setTotalCost(dto.getTotalCost());
            entity.setStatus(Reservation.Status.valueOf(dto.getStatus().toString()));
            entity.setDetails(dto.getDetails().stream().map(this::toEntity).toList());
        } catch (NullPointerException e) {
            log.error("This reservation is not in the catalog.");
            throw new ServiceException(MessageManager.getMessage("msg.empty"));
        }
        return entity;
    }

    /**
     * Method transforms object ReservationInfo into objectReservationInfoDto
     *
     * @param entity ReservationInfo
     * @return object RReservationInfoDto
     */
    public ReservationInfoDto toDto(ReservationInfo entity) {
        log.debug("Calling a service method toDto. ReservationInfo = {}", entity);
        ReservationInfoDto dto = new ReservationInfoDto();
        try {
            dto.setId(entity.getId());
            dto.setReservationId(entity.getReservation().getId());
            dto.setRoom(toDto(entity.getRoom()));
            dto.setCheckIn(entity.getCheckIn());
            dto.setCheckOut(entity.getCheckOut());
            dto.setNights(entity.getNights());
            dto.setRoomPrice(entity.getRoomPrice());
        } catch (NullPointerException e) {
            log.error("This reservation info is not in the catalog.");
            throw new ServiceException(MessageManager.getMessage("msg.empty"));
        }
        return dto;
    }

    /**
     * Method transforms object ReservationInfoDto into object ReservationInfo
     *
     * @param dto ReservationInfoDto
     * @return object ReservationInfo
     */
    public ReservationInfo toEntity(ReservationInfoDto dto) {
        log.debug("Calling a service method toEntity. ReservationInfoDto = {}", dto);
        ReservationInfo entity = new ReservationInfo();
        try {
            entity.setId(dto.getId());
            entity.setReservation(reservationRepository.findById(dto.getReservationId()));
            entity.setRoom(toEntity(dto.getRoom()));
            entity.setCheckIn(dto.getCheckIn());
            entity.setCheckOut(dto.getCheckOut());
            entity.setNights(dto.getNights());
            entity.setRoomPrice(dto.getRoomPrice());
        } catch (NullPointerException e) {
            log.error("This reservation info is not in the catalog.");
            throw new ServiceException(MessageManager.getMessage("msg.empty"));
        }
        return entity;
    }
}
