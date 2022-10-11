package com.company.hotel_booking.managers;

/**
 * Class with SQL constants
 */
public class SqlManager {
    // SQL
    // Reservation
    public static final String SQL_RESERVATION_FIND_ALL = "from Reservation";
    public static final String SQL_RESERVATION_DELETE = "update Reservation SET deleted = true where id = :id";
    public static final String SQL_RESERVATION_COUNT_RESERVATIONS = "select count(*) from Reservation";
    public static final String SQL_RESERVATION_PAGE = "from Reservation ORDER BY id";
    public static final String SQL_RESERVATION_PAGE_BY_USER = "from Reservation where user_id=:userId ORDER BY id";
    public static final String SQL_RESERVATION_BY_USER = "from Reservation where user_id=:userId";

    // ReservationInfo
    public static final String SQL_RESERVATION_INFO_FIND_ALL = "from ReservationInfo";
    public static final String SQL_RESERVATION_INFO_DELETE = "update ReservationInfo SET deleted = true where id = :id";
    public static final String SQL_RESERVATION_INFO_PAGE = "from ReservationInfo ORDER BY id";
    public static final String SQL_RESERVATION_INFO_COUNT_RESERVATIONS_INFO = "select count(*) from ReservationInfo";

    // Room
    public static final String SQL_ROOM_FIND_ALL = "from Room";
    public static final String SQL_ROOM_DELETE = "update Room SET deleted = true where id = :id";
    public static final String SQL_ROOM_FIND_BY_NUMBER = "from Room where room_number = :number";
    public static final String SQL_ROOM_COUNT_ROOMS = "select count(*) from Room";
    public static final String SQL_ROOM_FIND_AVAILABLE_ROOMS = "SELECT r.id, r.room_number, t.name AS type, " +
            "c.name AS capacity, r.price, s.name AS status FROM rooms r JOIN room_type t ON r.room_type_id = t.id " +
            "JOIN room_capacity c ON r.room_capacity_id = c.id  JOIN room_status s ON r.room_status_id = s.id " +
            "WHERE r.room_status_id=1 AND r.room_type_id=?) AND r.room_capacity_id=?) " +
            "AND NOT EXISTS (SELECT NULL FROM reservation_info i JOIN reservations rv " +
            "ON rv.id = i.reservation_id WHERE i.room_id = r.id AND (rv.status_id=(SELECT id " +
            "FROM reservation_statuses rs WHERE rs.name='CONFIRMED') AND ((? between i.check_in AND i.check_out) " +
            "OR (? between i.check_in AND i.check_out) OR ((? < i.check_in) AND (? > i.check_out)))))";

    public static final String SQL_ROOM_FIND_AVAILABLE_ROOMS2 = "from Room r where r.room_status_id=1 and r.room_type_id=" +
            ":type_id and r.room_capacity_id=:capacity_id and not exists (select nullif(1,1) from ReservationInfo i join " +
            "Reservation rv ON rv.id = i.reservation_id where i.room_id = r.id and rv.status_id=2 and " +
            "((:check_in1 between i.check_in and i.check_out) or (check_out1 between i.check_in and i.check_out) or " +
            "((check_in2 < i.check_in) and (check_out2 > i.check_out)))";
    public static final String SQL_ROOM_PAGE = "from Room ORDER BY room_number";

    // User
    public static final String SQL_USER_FIND_ALL = "from User where block = false";
    public static final String SQL_USER_DELETE = "update User SET deleted = true where id = :id";

    public static final String SQL_USER_BLOCK = "update User SET block = true where id = :id";
    public static final String SQL_USER_FIND_BY_EMAIL = "from User where email = :email and block = false";
    public static final String SQL_USER_COUNT_USERS = "select count(*) from User";
    public static final String SQL_USER_PAGE = "from User ORDER BY last_name";
}