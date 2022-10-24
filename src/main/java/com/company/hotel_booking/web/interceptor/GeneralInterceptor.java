package com.company.hotel_booking.web.interceptor;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
public class GeneralInterceptor implements HandlerInterceptor{
    @Override
    @LogInvocation
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler){
        log.info("Interceptor-PRE: " + request.getRequestURI() + " method: " + request.getMethod());
        return true;
    }

    @Override
    @LogInvocation
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView){
        log.info("Interceptor-POST: " + request.getRequestURI() + " method: " + request.getMethod());
    }

    @Override
    @LogInvocation
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex){
        log.info("Interceptor-AFTER: " + request.getRequestURI() + " method: " + request.getMethod());
    }
}