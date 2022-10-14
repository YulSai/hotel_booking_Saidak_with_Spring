package com.company.hotel_booking.controller.filters;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Class with filter for messages
 */
@WebFilter(urlPatterns = {"/*", "/controller/*"})
public class MessageFilter extends HttpFilter {

    @Override
    @LogInvocation
    protected void doFilter(HttpServletRequest req, HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            session.removeAttribute("message");
        }
        chain.doFilter(req, res);
    }
}