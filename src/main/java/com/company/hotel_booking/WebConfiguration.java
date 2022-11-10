package com.company.hotel_booking;


import com.company.hotel_booking.utils.constants.PagesConstants;
import com.company.hotel_booking.web.filters.AuthorizationFilter;
import com.company.hotel_booking.web.interceptors.GeneralInterceptor;
import com.company.hotel_booking.web.interceptors.MessageInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Class Spring configuration for web
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName(PagesConstants.PAGE_INDEX);
        registry.addViewController("/login").setViewName(PagesConstants.PAGE_LOGIN);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageBundleResrc =new ResourceBundleMessageSource();
        messageBundleResrc.setBasename("messages");
        return messageBundleResrc;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.UK);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeInterceptor() {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public GeneralInterceptor generalInterceptor() {
        return new GeneralInterceptor();
    }

    @Bean
    public MessageInterceptor messageInterceptor(){
        return new MessageInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(generalInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(localeInterceptor());
        registry.addInterceptor(messageInterceptor());
    }

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthorizationFilter(messageSource()));
        registration.addUrlPatterns("/users/*", "/rooms/*", "/reservations/*");
        registration.setOrder(2);
        return registration;
    }
}