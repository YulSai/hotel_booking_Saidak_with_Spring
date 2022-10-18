package com.company.hotel_booking.managers;

/**
 * Class with SQL constants
 */
public class SqlManager {
    // SQL
    // Reservation
    public static final String SQL_RESERVATION_DELETE = "UPDATE reservations SET deleted = true WHERE id=?";

    // ReservationInfo
    public static final String SQL_RESERVATION_INFO_DELETE = "UPDATE reservation_info SET deleted = true WHERE id=?";

    // Room
    public static final String SQL_ROOM_DELETE = "UPDATE rooms SET deleted = true WHERE id=?";
    public static final String SQL_ROOM_FIND_AVAILABLE_ROOMS =
            "SELECT r.id, r.room_number, r.room_type_id, r.room_capacity_id, r.price, r.room_status_id  " +
                    "FROM rooms r " +
                    "JOIN room_type t ON r.room_type_id = t.id " +
                    "JOIN room_capacity c ON r.room_capacity_id = c.id " +
                    "JOIN room_status s ON r.room_status_id = s.id " +
                    "WHERE r.room_status_id=1 " +
                    "AND r.room_type_id=? " +
                    "AND r.room_capacity_id=? " +
                    "AND NOT EXISTS (SELECT NULL FROM reservation_info i " +
                    "JOIN reservations rv ON rv.id = i.reservation_id " +
                    "WHERE i.room_id = r.id AND rv.status_id=2 " +
                    "AND ((? between i.check_in AND i.check_out) OR (? between i.check_in AND i.check_out) " +
                    "OR ((? < i.check_in) AND (? > i.check_out))))";

    // User
    public static final String SQL_USER_DELETE = "UPDATE users SET deleted = true WHERE id=?";
    public static final String SQL_USER_BLOCK = "update User SET block = true where id = :id";
}