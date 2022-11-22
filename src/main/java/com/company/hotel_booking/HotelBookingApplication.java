package com.company.hotel_booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class Spring configuration with entry-point
 */
@SpringBootApplication
public class HotelBookingApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingApplication.class, args);
    }
}