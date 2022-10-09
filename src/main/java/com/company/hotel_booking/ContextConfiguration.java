package com.company.hotel_booking;


import com.company.hotel_booking.exceptions.ConnectionPoolException;
import com.company.hotel_booking.managers.ConfigurationManager;
import com.company.hotel_booking.managers.MessageManager;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@Log4j2
public class ContextConfiguration {
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        ConfigurationManager configurationManager = new ConfigurationManager();
        try {
            // TODO Is Class.forName here? Is it necessary?
            Class.forName(configurationManager.getProperty(configurationManager.DB_DRIVER));
            dataSource.setJdbcUrl(configurationManager.getProperty(configurationManager.DB_URL));
            dataSource.setUsername(configurationManager.getProperty(configurationManager.DB_LOGIN));
            dataSource.setPassword(configurationManager.getProperty(configurationManager.DB_PASSWORD));
            dataSource.setDriverClassName(configurationManager.getProperty(configurationManager.DB_DRIVER));
            return dataSource;
        } catch (ClassNotFoundException e) {
            log.error("Error connecting to database", e);
            throw new ConnectionPoolException(MessageManager.getMessage("msg.error.driver"), e);
        }
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
