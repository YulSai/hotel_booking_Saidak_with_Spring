//package com.company.hotel_booking.web.filters;
//
//import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
//import com.company.hotel_booking.utils.managers.MessageManager;
//import com.company.hotel_booking.utils.managers.PagesManager;
//import com.company.hotel_booking.service.dto.UserDto;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * Class with filter for user access levels
// */
//@WebFilter(urlPatterns = "/controller/*")
//public class UserRoleFilter extends HttpFilter {
//
//    /**
//     * Method gets user's role
//     *
//     * @param session HttpSession
//     * @return user's role
//     */
//    private String getRole(HttpSession session) {
//        String role = "GUEST";
//        if (session.getAttribute("user") != null) {
//            UserDto user = (UserDto) session.getAttribute("user");
//            role = user.getRole().toString();
//        }
//        return role;
//    }
//
//    /**
//     * Method checks the access level
//     *
//     * @param command given command
//     * @param role    user's role
//     */
//    private void verifyAccessLevel(HttpServletRequest req, HttpServletResponse res, String command,
//                                   String role) throws ServletException, IOException {
//        SecurityLevel levelUser = SecurityLevel.valueOf(role);
//        SecurityLevel levelCommand = CommandResolver.getINSTANCE().getSecurityLevel(command);
//        if (levelUser.ordinal() < levelCommand.ordinal()) {
//            req.setAttribute("status", 404);
//            req.setAttribute("message", MessageManager.getMessage("msg.insufficient.rights"));
//            req.getRequestDispatcher(PagesManager.PAGE_ERROR).forward(req, res);
//        }
//    }
//
//    @Override
//    @LogInvocation
//    protected void doFilter(HttpServletRequest req, HttpServletResponse res,
//                            FilterChain chain) throws ServletException, IOException {
//        String command = req.getParameter("command");
//        HttpSession session = req.getSession(false);
//        String role = getRole(session);
//        verifyAccessLevel(req, res, command, role);
//        chain.doFilter(req, res);
//    }
//}