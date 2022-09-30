//package com.company.hotel_booking.dao.impl;
//
//import com.company.hotel_booking.dao.api.IRoomDao;
//import com.company.hotel_booking.dao.connection.DataSource;
//import com.company.hotel_booking.dao.entity.Room;
//import com.company.hotel_booking.managers.ConfigurationManager;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class RoomDaoImplTest {
//    private final IRoomDao roomDao = new RoomDaoImpl(new DataSource(new ConfigurationManager()));
//
//    @Test
//    public void findById() {
//        Room actual = roomDao.findById(1L);
//        Room expected = getExpectedRoom();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void findAvailableRooms() {
//        LocalDate check_in = LocalDate.parse("2022-08-05");
//        LocalDate check_out = LocalDate.parse("2022-08-11");
//        String type = "STANDARD";
//        String capacity = "SINGLE";
//        List<Room> actual = roomDao.findAvailableRooms(check_in, check_out, type, capacity);
//        List<Room> expected = getExpectedListRoom();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void findRoomByNumber() {
//        Room actual = roomDao.findRoomByNumber("101S");
//        Room expected = getExpectedRoom();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void countRow() {
//        Long actual = roomDao.countRow();
//        Long expected = 64L;
//        assertEquals(expected, actual);
//    }
//
//    private Room getExpectedRoom() {
//        return new Room(1L, "101S",
//                Room.RoomType.valueOf("STANDARD"),
//                Room.Capacity.valueOf("SINGLE"),
//                BigDecimal.valueOf(100),
//                Room.RoomStatus.valueOf("AVAILABLE"));
//    }
//
//    private List<Room> getExpectedListRoom() {
//        List<Room> expected = new ArrayList<>();
//        expected.add(new Room(1L, "101S",
//                Room.RoomType.valueOf("STANDARD"),
//                Room.Capacity.valueOf("SINGLE"),
//                BigDecimal.valueOf(100),
//                Room.RoomStatus.valueOf("AVAILABLE")));
//        expected.add(new Room(9L, "301S",
//                Room.RoomType.valueOf("STANDARD"),
//                Room.Capacity.valueOf("SINGLE"),
//                BigDecimal.valueOf(100),
//                Room.RoomStatus.valueOf("AVAILABLE")));
//        expected.add(new Room(13L, "401S",
//                Room.RoomType.valueOf("STANDARD"),
//                Room.Capacity.valueOf("SINGLE"),
//                BigDecimal.valueOf(100),
//                Room.RoomStatus.valueOf("AVAILABLE")));
//        return expected;
//    }
//}