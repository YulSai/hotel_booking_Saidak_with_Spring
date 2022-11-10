package com.company.hotel_booking.web.interceptors;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Interceptor for handling messages passed to the session
 */
@Log4j2
public class MessageInterceptor implements HandlerInterceptor {
    @Override
    @LogInvocation
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("message");
        if (message != null) {
            log.info("MessageInterceptor: message retrieved from session" + request.getRequestURI() +
                    " method: " + request.getMethod());
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }
        return true;
    }
}