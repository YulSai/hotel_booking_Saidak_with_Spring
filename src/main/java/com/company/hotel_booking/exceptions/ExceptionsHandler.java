package com.company.hotel_booking.exceptions;

import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionsHandler {
    public String handleException(HttpServletRequest req, HttpServletResponse res, Exception e) {
        String page;
        String message;
        int status;

        if (e instanceof DaoException) {
            status = 400;
            message = e.getMessage();
            page = PagesManager.PAGE_ERROR;
        } else if (e instanceof ServiceException) {
            status = 400;
            message = e.getMessage();
            page = PagesManager.PAGE_ERROR;
        } else if (e instanceof LoginUserException) {
            status = 400;
            message = MessageManager.getMessage("msg.incorrect.email.password");
            page = PagesManager.PAGE_LOGIN;
        } else if (e instanceof RegistrationException) {
            status = 400;
            message = e.getMessage();
            page = PagesManager.PAGE_CREATE_USER;
        } else if (e instanceof NotFoundException) {
            status = 404;
            message = MessageManager.getMessage("msg.not.found");
            page = PagesManager.PAGE_404;
        } else if (e instanceof ConnectionPoolException) {
            status = 404;
            message = e.getMessage();
            page = PagesManager.PAGE_404;
        } else {
            status = 500;
            message = MessageManager.getMessage("msg.internal.error");
            page = PagesManager.PAGE_ERROR;
        }
        req.setAttribute("status", status);
        req.setAttribute("message", message);
        res.setStatus(status);
        return page;
    }
}