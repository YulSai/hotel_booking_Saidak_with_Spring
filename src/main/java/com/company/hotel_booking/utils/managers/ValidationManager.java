package com.company.hotel_booking.utils.managers;

/**
 * Class with Regex constants for validation
 */
public class ValidationManager {
    public static final String NAME = "^[A-Za-z-А-Яа-я]+";
    public static final String PASSWORD = "[A-Za-z0-9_]+";
    public static final String PHONE = "\\+{1}[0-9]{10,15}";
    public static final String ROOM_NUMBER = "\\d{3}[A-Z]{1}";

}