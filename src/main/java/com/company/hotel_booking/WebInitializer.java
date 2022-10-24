package com.company.hotel_booking;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ContextConfiguration.class);

        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("DispatcherServlet", servlet);
        MultipartConfigElement multipartConfig = new MultipartConfigElement
                ("C:/Users/Yulia S/IdeaProjects/hotel_booking_Saidak/src/main/resources/static/images/");
        registration.setMultipartConfig(multipartConfig);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}