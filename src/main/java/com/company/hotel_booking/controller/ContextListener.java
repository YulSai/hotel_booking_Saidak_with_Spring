package com.company.hotel_booking.controller;

import com.company.hotel_booking.ContextConfiguration;
import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class ContextListener implements ServletContextListener {
    public static AnnotationConfigApplicationContext context;

    @Override
    @LogInvocation
    public void contextInitialized(ServletContextEvent sce) {
        context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
    }

    @Override
    @LogInvocation
    public void contextDestroyed(ServletContextEvent sce) {
        context.close();
    }
}
