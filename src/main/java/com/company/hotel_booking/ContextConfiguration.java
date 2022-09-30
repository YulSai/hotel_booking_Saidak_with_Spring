package com.company.hotel_booking;

import com.company.hotel_booking.dao.connection.DataSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PreDestroy;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class ContextConfiguration {
    private final DataSource dataSource;

    public ContextConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @PreDestroy
    public void destroy() {
        dataSource.close();
    }
}
