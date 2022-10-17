package com.company.hotel_booking.managers;

import com.company.hotel_booking.exceptions.ConnectionPoolException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class with configuration constants and for getting settings
 */
@Component
public class ConfigurationManager {
    private static final Properties properties = new Properties();
    private final String RESOURCE_NAME = "/application.properties";
    // DataBase Connection Properties
    public final String PROFILE = "dev";
    public final String DB_URL = "db." + PROFILE + ".url";
    public final String DB_LOGIN = "db." + PROFILE + ".login";
    public final String DB_PASSWORD = "db." + PROFILE + ".password";
    public final String DB_DRIVER = "db.driver";

    public ConfigurationManager() {
        try (InputStream in = getClass().getResourceAsStream(RESOURCE_NAME)) {
            properties.load(in);
        } catch (IOException e) {
            throw new ConnectionPoolException(MessageManager.getMessage("msg.error.create"), e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}