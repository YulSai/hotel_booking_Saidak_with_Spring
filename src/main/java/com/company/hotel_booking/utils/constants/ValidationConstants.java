package com.company.hotel_booking.utils.constants;

/**
 * Class with Regex constants for validation
 */
public class ValidationConstants {
    public static final String NAME = "^[A-Za-z-А-Яа-я]+";

    public static final String USERNAME = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";
    public static final String PASSWORD = "[(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9.!@#$%^&*a-zA-Z]{6,}]+";
    public static final String PHONE = "\\+{1}[0-9]{10,15}";
    public static final String ROOM_NUMBER = "\\d{3}[A-Z]{1}";



}