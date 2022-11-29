package com.company.hotel_booking.utils;

import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Class with test values Dto entities
 */
public class DtoTest {

    public static UserDto getExpectedUserWithoutId() {
        UserDto userDto = new UserDto();
        userDto.setUsername(TestConstants.USER_USERNAME);
        userDto.setPassword(TestConstants.USER_PASSWORD);
        userDto.setFirstName(TestConstants.USER_FIRSTNAME);
        userDto.setLastName(TestConstants.USER_LASTNAME);
        userDto.setEmail(TestConstants.USER_EMAIL);
        userDto.setPhoneNumber(TestConstants.USER_PHONE_NUMBER);
        userDto.setRole(UserDto.RoleDto.valueOf(TestConstants.USER_ROLE_ADMIN));
        userDto.setAvatar(TestConstants.USER_AVATAR);
        userDto.setBlock(TestConstants.USER_BLOCK);
        return userDto;
    }

    public static UserDto getExpectedUserWithId() {
        UserDto userDto = getExpectedUserWithoutId();
        userDto.setId(TestConstants.USER_ID);
        return userDto;
    }

    public static UserDto getExpectedUserFormCreate() {
        UserDto userDto = new UserDto();
        userDto.setUsername(TestConstants.USER_USERNAME);
        userDto.setPassword(TestConstants.USER_PASSWORD);
        userDto.setFirstName(TestConstants.USER_FIRSTNAME);
        userDto.setLastName(TestConstants.USER_LASTNAME);
        userDto.setEmail(TestConstants.USER_EMAIL);
        userDto.setPhoneNumber(TestConstants.USER_PHONE_NUMBER);
        return userDto;
    }

    public static UserDto getExpectedUserFormCreateWithId() {
        UserDto userDto = getExpectedUserFormCreate();
        userDto.setId(TestConstants.USER_ID);
        userDto.setRole(UserDto.RoleDto.valueOf(TestConstants.USER_ROLE_CLIENT));
        userDto.setAvatar(TestConstants.USER_AVATAR);
        return userDto;
    }

    public static RoomDto getExpectedRoomWithoutId() {
        RoomDto room = new RoomDto();
        room.setNumber(TestConstants.ROOM_NUMBER);
        room.setType(RoomDto.RoomTypeDto.valueOf(TestConstants.ROOM_TYPE));
        room.setCapacity(RoomDto.CapacityDto.valueOf(TestConstants.ROOM_CAPACITY));
        room.setPrice(TestConstants.ROOM_PRICE);
        room.setStatus(RoomDto.RoomStatusDto.valueOf(TestConstants.ROOM_STATUS));
        return room;
    }

    public static RoomDto getExpectedRoomWithId() {
        RoomDto room = getExpectedRoomWithoutId();
        room.setId(TestConstants.ROOM_ID);
        return room;
    }

    public static ReservationDto getExpectedReservationWithoutId() {
        ReservationDto reservation = new ReservationDto();
        reservation.setUser(getExpectedUserWithId());
        reservation.setTotalCost(TestConstants.RESERVATION_TOTAL_COST);
        reservation.setStatus(ReservationDto.StatusDto.valueOf(TestConstants.RESERVATION_STATUS));
        reservation.addDetails(getExpectedReservationInfoWithId());
        return reservation;
    }

    public static ReservationDto getExpectedReservationWithId() {
        ReservationDto reservation = getExpectedReservationWithoutId();
        reservation.setId(TestConstants.RESERVATION_ID);
        return reservation;
    }

    public static ReservationInfoDto getExpectedReservationInfoWithoutId() {
        ReservationInfoDto info = new ReservationInfoDto();
        info.setRoom(getExpectedRoomWithId());
        info.setCheckIn(TestConstants.RESERVATION_INFO_CHECKIN);
        info.setCheckOut(TestConstants.RESERVATION_INFO_CHECKOUT);
        info.setRoomPrice(TestConstants.ROOM_PRICE);
        return info;
    }

    public static ReservationInfoDto getExpectedReservationInfoWithId() {
        ReservationInfoDto info = getExpectedReservationInfoWithoutId();
        info.setId(TestConstants.RESERVATION_INFO_ID);
        return info;
    }

    public static Map<Long, Long> getBooking() {
        LocalDate checkIn = LocalDate.parse("2022-11-28");
        LocalDate checkOut = LocalDate.parse("2022-11-30");

        Map<Long, Long> booking = new HashMap<>();
        booking.put(getExpectedRoomWithId().getId(), ChronoUnit.DAYS.between(checkIn, checkOut));
        return booking;
    }

    /**
     * Method converts the object to JSON
     *
     * @param object Dto object
     * @return object as byte
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
