package com.company.hotel_booking.web.filters;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.managers.MessageManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class with filter for authorization
 */
@WebFilter(urlPatterns = {"/users/all", "/users/{id}", "/users/update", "/users/delete", "/users/change_password",
        "/rooms/all", "/rooms/{id}", "/rooms/update", "/rooms/create", "/reservations/all", "/reservations/{id}",
        "/reservations/update", "/reservations/create", "/reservations/cancel_reservation",
        "/reservations/user_reservations"})
public class AuthorizationFilter extends HttpFilter {
    @Override
    @LogInvocation
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            req.setAttribute("message", MessageManager.getMessage("msg.login"));
            res.sendRedirect("/login");
            return;
        }
        chain.doFilter(req, res);
    }
}