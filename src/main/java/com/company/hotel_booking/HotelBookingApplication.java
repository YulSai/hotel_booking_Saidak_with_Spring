package com.company.hotel_booking;

import com.company.hotel_booking.utils.managers.MessageManager;
import com.company.hotel_booking.web.filters.AuthorizationFilter;
import com.company.hotel_booking.web.interceptor.GeneralInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Locale;

/**
 * Class Spring configuration and entry-point
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class HotelBookingApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingApplication.class, args);
    }

    @Bean
    public MessageManager messageManager() {
        return new MessageManager(Locale.UK);
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public GeneralInterceptor generalInterceptor() {
        return new GeneralInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(generalInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthorizationFilter(messageManager()));
        registration.addUrlPatterns("/users/all", "/users/{id}", "/users/update", "/users/delete", "/users/change_password",
                "/rooms/all", "/rooms/{id}", "/rooms/update", "/rooms/create", "/reservations/all", "/reservations/{id}",
                "/reservations/update", "/reservations/create", "/reservations/cancel_reservation",
                "/reservations/user_reservations");
        registration.setOrder(2);
        return registration;
    }
}