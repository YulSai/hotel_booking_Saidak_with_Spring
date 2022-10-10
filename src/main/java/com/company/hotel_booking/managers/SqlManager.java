package com.company.hotel_booking.managers;

/**
 * Class with SQL constants
 */
public class SqlManager {
    // SQL
    // Reservation
    public static final String SQL_RESERVATION_FIND_BY_ID = "SELECT rs.id, rs.user_id, rs.total_cost, sr.name AS status " +
            "FROM reservations rs JOIN reservation_statuses sr ON sr.id = rs.status_id WHERE rs.id=? AND rs.deleted=false";
    public static final String SQL_RESERVATION_FIND_ALL = "SELECT rs.id, rs.user_id, rs.total_cost, sr.name AS status " +
            "FROM reservations rs JOIN reservation_statuses sr ON sr.id = rs.status_id WHERE rs.deleted=false " +
            "AND rs.status_id!=(SELECT id FROM reservation_statuses s WHERE s.name='DELETED')";
    public static final String SQL_RESERVATION_CREATE = "INSERT INTO reservations (user_id, total_cost, status_id) " +
            "VALUES (?, ?, (SELECT id FROM reservation_statuses s WHERE s.name=?))";
    public static final String SQL_RESERVATION_UPDATE = "UPDATE reservations SET user_id=?, total_cost=?, status_id=" +
            "(SELECT s.id FROM reservation_statuses s WHERE s.name=?) WHERE id=? AND deleted=false";
    public static final String SQL_RESERVATION_DELETE = "UPDATE reservations SET deleted=true WHERE id=?;";
    public static final String SQL_RESERVATION_COUNT_RESERVATIONS = "SELECT COUNT(*) AS total FROM reservations WHERE " +
            "status_id!=(SELECT id FROM reservation_statuses s WHERE s.name='DELETED') AND deleted = false";
    public static final String SQL_RESERVATION_PAGE = "SELECT rs.id, rs.user_id, rs.total_cost, sr.name AS status " +
            "FROM reservations rs JOIN reservation_statuses sr ON sr.id = rs.status_id WHERE rs.deleted = false " +
            "AND rs.status_id!=(SELECT id FROM reservation_statuses s WHERE s.name='DELETED') " +
            "ORDER BY id LIMIT ? OFFSET ?";
    public static final String SQL_RESERVATION_PAGE_BY_USER = "SELECT rs.id, rs.user_id, rs.total_cost, sr.name AS status " +
            "FROM reservations rs JOIN reservation_statuses sr ON sr.id = rs.status_id WHERE rs.user_id=? " +
            "AND rs.deleted=false ORDER BY rs.id LIMIT ? OFFSET ?";
    public static final String SQL_RESERVATION_BY_USER = "SELECT rs.id, rs.user_id, rs.total_cost, sr.name AS status " +
            "FROM reservations rs JOIN reservation_statuses sr ON sr.id = rs.status_id " +
            "WHERE rs.user_id=? AND rs.deleted=false";

    // ReservationInfo
    public static final String SQL_RESERVATION_INFO_FIND_BY_ID = "SELECT i.id, i.reservation_id, i.room_id, i.check_in, " +
            "i.check_out, i.nights, i.room_price FROM reservation_info i WHERE i.id=? AND i.deleted=false";
    public static final String SQL_RESERVATION_INFO_FIND_ALL = "SELECT i.id, i.reservation_id, i.room_id, i.check_in, " +
            "i.check_out, i.nights, i.room_price FROM reservation_info i WHERE i.deleted=false";
    public static final String SQL_RESERVATION_INFO_CREATE = "INSERT INTO reservation_info (reservation_id, room_id, " +
            "check_in, check_out, nights, room_price) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_RESERVATION_INFO_DELETE = " UPDATE reservation_info SET deleted=true WHERE id=?";
    public static final String SQL_RESERVATION_INFO_PAGE = "SELECT i.id, i.reservation_id, i.room_id, i.check_in, " +
            "i.check_out, i.nights, i.room_price FROM reservation_info i WHERE i.deleted = false ORDER BY id LIMIT ? " +
            "OFFSET ?";
    public static final String SQL_RESERVATION_INFO_COUNT_RESERVATIONS_INFO = "SELECT COUNT(*) AS total " +
            "FROM reservation_info WHERE deleted = false";
    public static final String SQL_RESERVATION_INFO_FIND_BY_RESERVATION_ID = "SELECT i.id, i.reservation_id, i.room_id, " +
            "i.check_in, i.check_out, i.nights, i.room_price FROM reservation_info i  WHERE i.reservation_id=? " +
            "AND i.deleted=false";

    // Room
    public static final String SQL_ROOM_FIND_BY_ID = "SELECT r.id, r.room_number, t.name AS type, c.name AS capacity, " +
            "r.price, s.name AS status FROM rooms r JOIN room_type t ON r.room_type_id = t.id JOIN room_capacity c " +
            "ON r.room_capacity_id = c.id JOIN room_status s ON r.room_status_id = s.id WHERE r.id = ? " +
            "AND r.deleted = false";
    public static final String SQL_ROOM_FIND_ALL = "SELECT r.id, r.room_number, t.name AS type, c.name AS capacity, " +
            "r.price, s.name AS status FROM rooms r JOIN room_type t ON r.room_type_id = t.id JOIN room_capacity c " +
            "ON r.room_capacity_id = c.id JOIN room_status s ON r.room_status_id = s.id WHERE r.deleted = false";
    public static final String SQL_ROOM_CREATE = "INSERT INTO rooms (room_number, room_type_id, room_capacity_id, price, " +
            "room_status_id) VALUES (?, (SELECT id FROM room_type t WHERE t.name=?), (SELECT id FROM room_capacity c " +
            "WHERE c.name=?), ?, (SELECT id FROM room_status s WHERE s.name=?))";
    public static final String SQL_ROOM_UPDATE = "UPDATE rooms r SET room_number=?, room_type_id=(SELECT id FROM " +
            "room_type t WHERE t.name=?), room_capacity_id=(SELECT id FROM room_capacity c WHERE c.name=?), price=?, " +
            "room_status_id=(SELECT id FROM room_status s WHERE s.name=?) WHERE id = ? AND r.deleted = false";
    public static final String SQL_ROOM_DELETE = "UPDATE rooms SET deleted = true WHERE id = ?";
    public static final String SQL_ROOM_FIND_BY_NUMBER = "SELECT r.id, r.room_number, t.name AS type, c.name " +
            "AS capacity, r.price, s.name AS status FROM rooms r JOIN room_type t ON r.room_type_id = t.id " +
            "JOIN room_capacity c ON r.room_capacity_id = c.id JOIN room_status s ON r.room_status_id = s.id " +
            "WHERE r.room_number = ? AND r.deleted = false";
    public static final String SQL_ROOM_COUNT_ROOMS = "SELECT COUNT(*) AS total FROM rooms WHERE deleted = false";
    public static final String SQL_ROOM_FIND_AVAILABLE_ROOMS = "SELECT r.id, r.room_number, t.name AS type, c.name AS " +
            "capacity, r.price, s.name AS status FROM rooms r JOIN room_type t ON r.room_type_id = t.id " +
            "JOIN room_capacity c ON r.room_capacity_id = c.id  JOIN room_status s ON r.room_status_id = s.id " +
            "WHERE r.room_status_id=(SELECT id FROM room_status s WHERE s.name='AVAILABLE') AND r.room_type_id=" +
            "(SELECT id FROM room_type t WHERE t.name=?) AND r.room_capacity_id=(SELECT id FROM room_capacity c " +
            "WHERE c.name=?) AND NOT EXISTS (SELECT NULL FROM reservation_info i JOIN reservations rv " +
            "ON rv.id = i.reservation_id WHERE i.room_id = r.id AND (rv.status_id=(SELECT id " +
            "FROM reservation_statuses rs WHERE rs.name='CONFIRMED') AND ((? between i.check_in AND i.check_out) " +
            "OR (? between i.check_in AND i.check_out) OR ((? < i.check_in) AND (? > i.check_out)))))";
    public static final String SQL_ROOM_PAGE = "SELECT r.id, r.room_number, t.name AS type, c.name AS capacity, r.price, " +
            "s.name AS status FROM rooms r JOIN room_type t ON r.room_type_id = t.id JOIN room_capacity c " +
            "ON r.room_capacity_id = c.id JOIN room_status s ON r.room_status_id = s.id WHERE r.deleted = false " +
            "ORDER BY r.room_number LIMIT ? OFFSET ?";

    // User
    public static final String SQL_USER_FIND_ALL = "from User";
    public static final String SQL_USER_DELETE = "update User SET deleted = true where id = :id";
    public static final String SQL_USER_FIND_BY_EMAIL = "from User where email = :email";
    public static final String SQL_USER_COUNT_USERS = "select count(*) from User";
    public static final String SQL_USER_PAGE = "from User ORDER BY last_name";
}