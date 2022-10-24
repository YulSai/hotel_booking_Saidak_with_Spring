package com.company.hotel_booking.utils.managers;

/**
 * Class with page constants
 */
public class PagesManager {
    // JSP
    // Index
    public static final String PAGE_INDEX = "index";

    // Error
    public static final String PAGE_ERROR = "error";
    public static final String PAGE_404 = "404";

    // Reservation
    public static final String PAGE_RESERVATIONS = "forms/reservations/reservations";
    public static final String PAGE_RESERVATION = "forms/reservations/reservation";
    public static final String PAGE_BOOKING = "forms/reservations/booking";
    public static final String PAGE_UPDATE_RESERVATION = "forms/reservations/updateReservationForm";

    // Room
    public static final String PAGE_ROOMS = "forms/rooms/rooms";
    public static final String PAGE_ROOM = "forms/rooms/room";
    public static final String PAGE_CREATE_ROOM = "forms/rooms/createRoomForm";
    public static final String PAGE_UPDATE_ROOM = "forms/rooms/updateRoomForm";
    public static final String PAGE_SEARCH_AVAILABLE_ROOMS = "forms/reservations/searchAvailableRoomsForm";
    public static final String PAGE_ROOMS_AVAILABLE = "forms/reservations/roomsAvailable";

    // User
    public static final String PAGE_USERS = "forms/users/users";
    public static final String PAGE_USER = "forms/users/userInfo";
    public static final String PAGE_CREATE_USER = "forms/users/createUserForm";
    public static final String PAGE_UPDATE_USERS = "forms/users/updateUserForm";
    public static final String PAGE_UPDATE_USERS_ROLE = "forms/users/updateUserRoleForm";
    public static final String PAGE_DELETE_USER = "forms/users/deleteUser";
    public static final String PAGE_CHANGE_PASSWORD = "forms/users/changePasswordForm";

    //Login
    public static final String PAGE_LOGIN = "forms/authorizations/loginForm";
}
