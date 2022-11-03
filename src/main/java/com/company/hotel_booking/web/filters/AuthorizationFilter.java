package com.company.hotel_booking.web.filters;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getServletPath();
        return path.startsWith("/users/create") ||
                path.startsWith("/rooms/search_available_rooms") ||
                path.startsWith("/rooms/rooms_available") ||
                path.startsWith("/reservations/add_booking") ||
                path.startsWith("/reservations/booking") ||
                path.startsWith("/reservations/clean_booking") ||
                path.startsWith("/reservations/delete_booking/*");
    }

    @Override
    @LogInvocation
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            request.setAttribute("message",
                    messageManager.getMessage("msg.login", null, LocaleContextHolder.getLocale()));
            response.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(request, response);
    }
}