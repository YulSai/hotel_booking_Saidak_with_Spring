package com.company.hotel_booking.managers;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for receiving text messages
 */
public class MessageManger {
    private static final String RESOURCE_NAME = "pageMessage";
    private static ResourceBundle resourceBundle;

    public MessageManger(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }

    public static String getMessage(String key) {
        return resourceBundle.getString(key);
    }
}