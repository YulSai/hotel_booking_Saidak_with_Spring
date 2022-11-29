package com.company.hotel_booking.utils;

import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.data.entity.ReservationInfo;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.data.entity.User;

/**
 * Class with test values Entity entities
 */
public class EntityTest {

    public static User getExpectedUserWithoutId() {
        User user = new User();
        user.setUsername(TestConstants.USER_USERNAME);
        user.setPassword(TestConstants.USER_PASSWORD);
        user.setFirstName(TestConstants.USER_FIRSTNAME);
        user.setLastName(TestConstants.USER_LASTNAME);
        user.setEmail(TestConstants.USER_EMAIL);
        user.setPhoneNumber(TestConstants.USER_PHONE_NUMBER);
        user.setRole(User.Role.valueOf(TestConstants.USER_ROLE_ADMIN));
        user.setAvatar(TestConstants.USER_AVATAR);
        user.setBlock(TestConstants.USER_BLOCK);
        return user;
    }

    public static User getExpectedUserWithId() {
        User user = getExpectedUserWithoutId();
        user.setId(TestConstants.USER_ID);
        return user;
    }

    public static Room getExpectedRoomWithoutId() {
        Room room = new Room();
        room.setNumber(TestConstants.ROOM_NUMBER);
        room.setType(Room.RoomType.valueOf(TestConstants.ROOM_TYPE));
        room.setCapacity(Room.Capacity.valueOf(TestConstants.ROOM_CAPACITY));
        room.setPrice(TestConstants.ROOM_PRICE);
        room.setStatus(Room.RoomStatus.valueOf(TestConstants.ROOM_STATUS));
        return room;
    }

    public static Room getExpectedRoomWithId() {
        Room room = getExpectedRoomWithoutId();
        room.setId(TestConstants.ROOM_ID);
        return room;
    }

    public static Reservation getExpectedReservationWithoutId() {
        Reservation reservation = new Reservation();
        reservation.setUser(getExpectedUserWithId());
        reservation.setTotalCost(TestConstants.RESERVATION_TOTAL_COST);
        reservation.setStatus(Reservation.Status.valueOf(TestConstants.RESERVATION_STATUS));
        reservation.addDetails(getExpectedReservationInfoWithId());
        return reservation;
    }

    public static Reservation getExpectedReservationWithId() {
        Reservation reservation = getExpectedReservationWithoutId();
        reservation.setId(TestConstants.RESERVATION_ID);
        return reservation;
    }

    public static ReservationInfo getExpectedReservationInfoWithoutId() {
        ReservationInfo info = new ReservationInfo();
        info.setRoom(getExpectedRoomWithId());
        info.setCheckIn(TestConstants.RESERVATION_INFO_CHECKIN);
        info.setCheckOut(TestConstants.RESERVATION_INFO_CHECKOUT);
        info.setRoomPrice(TestConstants.ROOM_PRICE);
        return info;
    }

    public static ReservationInfo getExpectedReservationInfoWithId() {
        ReservationInfo info = getExpectedReservationInfoWithoutId();
        info.setId(TestConstants.RESERVATION_INFO_ID);
        return info;
    }
}
