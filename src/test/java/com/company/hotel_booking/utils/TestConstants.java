package com.company.hotel_booking.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestConstants {

    public static final Long USER_ID = 1L;
    public static final String USER_USERNAME = "maxim_test";
    public static final String USER_PASSWORD = "$2a$10$UJV2JQ2l.ToXTJdwJQ1PgOdGMioGcUmy6AJY2BoQ6pQiXFGPQjfL";
    public static final String USER_FIRSTNAME = "Maxim";
    public static final String USER_LASTNAME = "Test";
    public static final String USER_EMAIL = "maxim_test@test.com";
    public static final String USER_PHONE_NUMBER = "+48511906624";
    public static final String USER_ROLE = "ADMIN";
    public static final String USER_AVATAR = "avatar_test.png";
    public static final boolean USER_BLOCK = false;

    public static final Long ROOM_ID = 1L;
    public static final String ROOM_NUMBER = "100S";
    public static final String ROOM_TYPE = "STANDARD";
    public static final String ROOM_CAPACITY = "SINGLE";
    public static final BigDecimal ROOM_PRICE = BigDecimal.valueOf(100);
    public static final String ROOM_STATUS = "AVAILABLE";

    public static final Long RESERVATION_ID = 1L;
    public static final BigDecimal RESERVATION_TOTAL_COST = BigDecimal.valueOf(200);
    public static final String RESERVATION_STATUS = "IN_PROGRESS";


    public static final Long RESERVATION_INFO_ID = 1L;
    public static final LocalDate RESERVATION_INFO_CHECKIN = LocalDate.parse("2022-11-24");
    public static final LocalDate RESERVATION_INFO_CHECKOUT = LocalDate.parse("2022-11-26");
}
