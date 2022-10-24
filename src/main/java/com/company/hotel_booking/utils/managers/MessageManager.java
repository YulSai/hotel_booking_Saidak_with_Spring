package com.company.hotel_booking.utils.managers;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for receiving text messages
 */

public class MessageManager {
    private static final String RESOURCE_NAME = "pageMessage";
    private final ResourceBundle resourceBundle;

    public MessageManager(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }
}