package com.company.hotel_booking.web.filters;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class with filter for authorization
 */
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
   private final MessageSource messageManager;

    @Override
    @LogInvocation
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().startsWith("users/create");
    }

    @LogInvocation
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            request.setAttribute("message", messageManager.getMessage("msg.login", null, request.getLocale()));
            response.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(request, response);
    }
}