package com.company.hotel_booking;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class Spring configuration
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@Log4j2
public class JavaConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("hotelBooking");
    }

    @Bean
    public TransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
}
