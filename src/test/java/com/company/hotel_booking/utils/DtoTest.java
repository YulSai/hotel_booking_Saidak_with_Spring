package com.company.hotel_booking.utils;

import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.ReservationInfoDto;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.service.dto.UserDto;

public class DtoTest {

    public static UserDto getExpectedUserWithoutId() {
        UserDto userDto = new UserDto();
        userDto.setUsername(TestConstants.USER_USERNAME);
        userDto.setPassword(TestConstants.USER_PASSWORD);
        userDto.setFirstName(TestConstants.USER_FIRSTNAME);
        userDto.setLastName(TestConstants.USER_LASTNAME);
        userDto.setEmail(TestConstants.USER_EMAIL);
        userDto.setPhoneNumber(TestConstants.USER_PHONE_NUMBER);
        userDto.setRole(UserDto.RoleDto.valueOf(TestConstants.USER_ROLE));
        userDto.setAvatar(TestConstants.USER_AVATAR);
        userDto.setBlock(TestConstants.USER_BLOCK);
        return userDto;
    }

    public static UserDto getExpectedUserWithId() {
        UserDto userDto = getExpectedUserWithoutId();
        userDto.setId(TestConstants.USER_ID);
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
}
