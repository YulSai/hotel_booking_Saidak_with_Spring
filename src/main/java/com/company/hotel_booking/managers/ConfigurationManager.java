package com.company.hotel_booking.managers;

import com.company.hotel_booking.exceptions.ConnectionPoolException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class with configuration constants and for getting settings
 */
@Log4j2
@Component
public class ConfigurationManager {
    private static final Properties properties = new Properties();
    private final String RESOURCE_NAME = "/application.properties";
    // DataBase Connection Properties
    public String PROFILE = "dev";
    public String DB_URL = "db." + PROFILE + ".url";
    public String DB_LOGIN = "db." + PROFILE + ".login";
    public String DB_PASSWORD = "db." + PROFILE + ".password";
    public String DB_DRIVER = "db.driver";

    public ConfigurationManager() {
        try (InputStream in = getClass().getResourceAsStream(RESOURCE_NAME)) {
            properties.load(in);
        } catch (IOException e) {
            log.error("Error with connection " + e);
            throw new ConnectionPoolException(MessageManger.getMessage("msg.error.create"), e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}