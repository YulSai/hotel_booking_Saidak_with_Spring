package com.company.hotel_booking.dao.connection;

import com.company.hotel_booking.managers.ConfigurationManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Class with methods for connecting and interacting with the database
 */
@Log4j2
@Component
public class DataSource implements AutoCloseable {
    private final String url;
    private final String login;
    private final String password;
    private final String driver;
    private ConnectionPool connectionPool;

    public DataSource(ConfigurationManager configurationManager) {
        url = configurationManager.getProperty(configurationManager.DB_URL);
        login = configurationManager.getProperty(configurationManager.DB_LOGIN);
        password = configurationManager.getProperty(configurationManager.DB_PASSWORD);
        driver = configurationManager.getProperty(configurationManager.DB_DRIVER);
    }

    /**
     * Method gets connection
     *
     * @return connection
     */
    public Connection getConnection() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool(driver, url, login, password, this);
            log.info("Connection pool initialized");
        }

        return connectionPool.getConnection();
    }

    ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    /**
     * Method for closing connection
     */
    @Override
    public void close() {
        if (connectionPool != null) {
            connectionPool.destroyPool();
            connectionPool = null;
            log.info("Database connection pool was destroyed");
        }
    }
}