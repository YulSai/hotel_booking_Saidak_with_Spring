package com.company.hotel_booking.controller;

import com.company.hotel_booking.JavaConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Log4j2
@WebListener
public class ContextListener implements ServletContextListener {
    public static AnnotationConfigApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context was initialized");
        context = new AnnotationConfigApplicationContext(JavaConfig.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Context was destroyed");
        context.close();
    }
}
