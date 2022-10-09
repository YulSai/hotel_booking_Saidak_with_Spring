package com.company.hotel_booking.controller.filters;

import com.company.hotel_booking.controller.command.api.CommandName;
import com.company.hotel_booking.controller.command.api.SecurityLevel;
import com.company.hotel_booking.controller.command.CommandResolver;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

/**
 * Class with filter for authorization
 */
@Log4j2
@WebFilter(urlPatterns = "/controller/*")
public class AuthorizationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String command = req.getParameter("command").toUpperCase();
        if (command == null || "".equals(command) || !CommandName.contains(command.toUpperCase())) {
            log.error("Incorrect address entered");
            req.setAttribute("status", 404);
            res.setStatus(404);
            req.getRequestDispatcher(PagesManager.PAGE_404).forward(req, res);
        } else {
            if (requiresAuthorization(command)) {
                HttpSession session = req.getSession(false);
                if (session == null || session.getAttribute("user") == null) {
                    req.setAttribute("message", MessageManager.getMessage("msg.login"));
                    req.getRequestDispatcher(PagesManager.PAGE_LOGIN).forward(req, res);
                    return;
                }
            }
            chain.doFilter(req, res);
        }
    }

    /**
     * Method checks if authorization is required to execute the given command
     *
     * @param command given command
     * @return true if authorization is required, otherwise - false
     */
    private boolean requiresAuthorization(String command) {
        SecurityLevel levelCommand = CommandResolver.getINSTANCE().getSecurityLevel(command);
        return switch (levelCommand) {
            case GUEST -> false;
            default -> true;
        };
    }
}