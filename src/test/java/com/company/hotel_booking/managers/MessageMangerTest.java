package com.company.hotel_booking.managers;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageMangerTest {

    @Test
    public void getMessageEn() {
        String actual = new MessageManger(Locale.UK).getMessage("msg.main.welcome");
        String expected = "Welcome to HotelBooking,";
        assertEquals(expected, actual);
    }

    @Test
    public void getMessageRu() {
        String actual = new MessageManger(new Locale("ru")).getMessage("msg.error.new.password");
        String expected = "Данный пароль уже был использован ранее - введите новый пароль";
        assertEquals(expected, actual);
    }
}