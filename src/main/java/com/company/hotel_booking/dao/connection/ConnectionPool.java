package com.company.hotel_booking.dao.connection;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Class with methods for creating and processing a connection pool
 */
@Log4j2
public class ConnectionPool {
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnections;

    /**
     * Constructor creates connection with database
     *
     * @param driver               database driver
     * @param url                  path to database
     * @param login                login for database
     * @param password             password for database
     */
    ConnectionPool(String driver, String url, String login, String password, DataSource dataSource) {
        freeConnections = new LinkedBlockingDeque<>(3);
        givenAwayConnections = new ArrayDeque<>();
        try {
            Class.forName(driver);
            for (int i = 0; i < 3; i++) {
                Connection connection = DriverManager.getConnection(url, login, password);
                freeConnections.offer(new ProxyConnection(connection, dataSource));
                log.info("Database connection created");
            }
        } catch (ClassNotFoundException | SQLException var7) {
            log.error("Error connecting to database", var7);
        }
    }

    /**
     * Method gets connection
     *
     * @return connection
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
            log.info("Connection got");
        } catch (InterruptedException var3) {
            log.error("Error getting connection", var3);
        }
        return connection;
    }

    /**
     * Method releases connection
     *
     * @param connection existing connection
     */
    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection proxy && givenAwayConnections.remove(connection)) {
            freeConnections.offer(proxy);
            log.info("Connection was released");
        } else {
            log.warn("Returned not proxy connection");
        }
    }

    /**
     * Method destroys pool connection
     */
    public void destroyPool() {
        for (int i = 0; i < 3; i++) {
            try {
                freeConnections.take().reallyClose();
                log.info("Connection closed");
            } catch (InterruptedException var3) {
                log.error("Error destroying the connection", var3);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            deregisterDrivers();
        }
    }

    /**
     * Method deregisters database driver
     */
    public void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining((driver) -> {
            try {
                DriverManager.deregisterDriver(driver);
                log.info("Driver = {} was deregistered", driver);
            } catch (SQLException var2) {
                log.error("Error driver deregistration", var2);
            }
        });
    }
}