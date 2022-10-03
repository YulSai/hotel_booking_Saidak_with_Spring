//package com.company.hotel_booking.dao.impl;
//
//import com.company.hotel_booking.data.api.dao.IReservationDao;
//import com.company.hotel_booking.data.api.dao.IReservationInfoDao;
//import com.company.hotel_booking.data.api.dao.IUserDao;
//import com.company.hotel_booking.dao.connection.DataSource;
//import com.company.hotel_booking.data.entity.dao.Reservation;
//import com.company.hotel_booking.data.entity.dao.ReservationInfo;
//import com.company.hotel_booking.data.entity.dao.Room;
//import com.company.hotel_booking.data.entity.dao.User;
//import com.company.hotel_booking.managers.ConfigurationManager;
//import com.zaxxer.hikari.HikariDataSource;
//import org.junit.jupiter.api.Test;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ReservationDaoImplTest {
//    private final DataSource dataSource = new DataSource(new ConfigurationManager());
//    private final IUserDao userDao = new UserDaoImpl(new JdbcTemplate(), new NamedParameterJdbcTemplate(new HikariDataSource()));
//    private final IReservationInfoDao reservationInfoDao = new ReservationInfoDaoImpl(dataSource,
//            new RoomDaoImpl(dataSource));
//    private final IReservationDao reservationDao = new ReservationDaoImpl(dataSource, userDao,
//            reservationInfoDao);
//
//    @Test
//    public void findById() {
//        Reservation actual = reservationDao.findById(8L);
//        Reservation expected = getExpectedReservation();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void findAllByUsers() {
//        List<Reservation> actual = reservationDao.findAllByUsers(15L);
//        List<Reservation> expected = getExpectedListReservation();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void countRow() {
//        Long actual = reservationDao.countRow();
//        Long expected = 10L;
//        assertEquals(expected, actual);
//    }
//
//    private List<Reservation> getExpectedListReservation() {
//        List<Reservation> list = new ArrayList<>();
//        list.add(getExpectedReservation());
//        return list;
//    }
//
//    private Reservation getExpectedReservation() {
//        Reservation reservation = new Reservation();
//        reservation.setId(8L);
//        reservation.setUser(getExpectedUser());
//        reservation.setTotalCost(BigDecimal.valueOf(1750));
//        reservation.setStatus(Reservation.Status.valueOf("CONFIRMED"));
//        reservation.setDetails(getExpectedListReservationInfo());
//        return reservation;
//    }
//
//    private User getExpectedUser() {
//        User user = new User();
//        user.setId(15L);
//        user.setFirstName("Nannie");
//        user.setLastName("Crawford");
//        user.setEmail("nannie_crawford@dayrep.com");
//        user.setPassword("3DE65B69A77DB9DD7B8279B10995552D6BAF1CB9");
//        user.setPhoneNumber("+38549782654");
//        user.setRole(User.Role.valueOf("CLIENT"));
//        user.setAvatar("avatar15.png");
//        return user;
//    }
//
//    private List<ReservationInfo> getExpectedListReservationInfo() {
//        List<ReservationInfo> details = new ArrayList<>();
//        details.add(getExpectedReservationInfo());
//        return details;
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