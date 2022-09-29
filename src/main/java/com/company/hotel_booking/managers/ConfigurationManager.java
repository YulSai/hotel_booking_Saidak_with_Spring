package com.company.hotel_booking.managers;

import java.util.ResourceBundle;

/**
 * Class with configuration constants and for getting settings
 */
public class ConfigurationManager {
    // DataBase Connection Properties
    public static final String PROFILE = "profile";
    public static final String DB_URL = "db." + ConfigurationManager.getInstance().getString(
            ConfigurationManager.PROFILE) + ".url";
    public static final String DB_LOGIN = "db." + ConfigurationManager.getInstance().getString(
            ConfigurationManager.PROFILE) + ".login";
    public static final String DB_PASSWORD = "db." + ConfigurationManager.getInstance().getString(
            ConfigurationManager.PROFILE) + ".password";
    public static final String DB_DRIVER = "db.driver";
    public static final String POOL_SIZE = "db.pool.size";

    private static final String RESOURCE_NAME = "application";
    private static ConfigurationManager INSTANCE;
    private final ResourceBundle resourceBundle;

    private ConfigurationManager() {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);
    }

    public static ConfigurationManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigurationManager();
        }
        return INSTANCE;
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}