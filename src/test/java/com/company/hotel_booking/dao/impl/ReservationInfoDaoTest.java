//package com.company.hotel_booking.dao.impl;
//
//import com.company.hotel_booking.data.api.dao.IReservationInfoDao;
//import com.company.hotel_booking.dao.connection.DataSource;
//import com.company.hotel_booking.data.entity.dao.ReservationInfo;
//import com.company.hotel_booking.data.entity.dao.Room;
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
//public class ReservationInfoDaoTest {
//    private final DataSource dataSource = new DataSource(new ConfigurationManager());
//    private final IReservationInfoDao reservationInfoDao = new ReservationInfoDaoImpl(dataSource,
//            new RoomDaoImpl(dataSource));
//
//    @Test
//    public void findById() {
//        ReservationInfo actual = reservationInfoDao.findById(8L);
//        ReservationInfo expected = getExpectedReservationInfo();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void findByReservationId() {
//        List<ReservationInfo> actual = reservationInfoDao.findByReservationId(8L);
//        List<ReservationInfo> expected = getExpectedListReservationInfo();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void countRow() {
//        Long actual = reservationInfoDao.countRow();
//        Long expected = 10L;
//        assertEquals(expected, actual);
//    }
//
//    private List<ReservationInfo> getExpectedListReservationInfo() {
//        List<ReservationInfo> list = new ArrayList<>();
//        list.add(getExpectedReservationInfo());
//        return list;
//    }
//
//    private ReservationInfo getExpectedReservationInfo() {
//        ReservationInfo info = new ReservationInfo();
//        info.setId(8L);
//        info.setReservationId(8L);
//        info.setRoom(getExpectedRoom());
//        info.setCheckIn(LocalDate.parse("2022-08-05"));
//        info.setCheckOut(LocalDate.parse("2022-08-12"));
//        info.setNights(7L);
//        info.setRoomPrice(BigDecimal.valueOf(250));
//        return info;
//    }
//
//    private Room getExpectedRoom() {
//        return new Room(8L, "204S",
//                Room.RoomType.valueOf("STANDARD"),
//                Room.Capacity.valueOf("FAMILY"),
//                BigDecimal.valueOf(250),
//                Room.RoomStatus.valueOf("AVAILABLE"));
//    }
//}