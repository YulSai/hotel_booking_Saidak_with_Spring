package com.company.hotel_booking.web.filters;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.managers.MessageManager;
import lombok.RequiredArgsConstructor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class with filter for authorization
 */
@RequiredArgsConstructor
public class AuthorizationFilter extends HttpFilter {
    private final MessageManager messageManager;
    @Override
    @LogInvocation
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            req.setAttribute("message", messageManager.getMessage("msg.login"));
            res.sendRedirect("/login");
            return;
        }
        chain.doFilter(req, res);
    }
}