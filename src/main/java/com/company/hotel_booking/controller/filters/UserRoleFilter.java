package com.company.hotel_booking.controller.filters;

import com.company.hotel_booking.controller.command.api.SecurityLevel;
import com.company.hotel_booking.controller.command.factory.CommandFactory;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.dto.UserDto;
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
 * Class with filter for user access levels
 */
@WebFilter(urlPatterns = "/controller/*")
@Log4j2
public class UserRoleFilter extends HttpFilter {

    /**
     * Method gets user's role
     *
     * @param session HttpSession
     * @return user's role
     */
    private String getRole(HttpSession session) {
        String role = "GUEST";
        if (session.getAttribute("user") != null) {
            UserDto user = (UserDto) session.getAttribute("user");
            role = user.getRole().toString();
        }
        return role;
    }

    /**
     * Method checks the access level
     *
     * @param command given command
     * @param role    user's role
     */
    private void verifyAccessLevel(HttpServletRequest req, HttpServletResponse res, String command,
                                   String role) throws ServletException, IOException {
        SecurityLevel levelUser = SecurityLevel.valueOf(role);
        SecurityLevel levelCommand = CommandFactory.getINSTANCE().getSecurityLevel(command);
        if (levelUser.ordinal() < levelCommand.ordinal()) {
            log.error("Insufficient access rights");
            req.setAttribute("status", 404);
            req.setAttribute("message", MessageManger.getMessage("msg.insufficient.rights"));
            req.getRequestDispatcher(PagesManager.PAGE_ERROR).forward(req, res);
        }
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res,
                            FilterChain chain) throws ServletException, IOException {
        String command = req.getParameter("command");
        HttpSession session = req.getSession(false);
        String role = getRole(session);
        verifyAccessLevel(req, res, command, role);
        chain.doFilter(req, res);
    }
}