package com.company.hotel_booking.exceptions;

import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ExceptionsHandler {
    public String handleException(HttpServletRequest req, HttpServletResponse res, Exception e) {
        String page;
        String message;
        int status;

        if (e instanceof DaoException) {
            log.error("DaoException");
            status = 400;
            message = e.getMessage();
            page = PagesManager.PAGE_ERROR;
        } else if (e instanceof ServiceException) {
            log.error("ServiceException");
            status = 400;
            message = e.getMessage();
            page = PagesManager.PAGE_ERROR;
        } else if (e instanceof LoginUserException) {
            log.error("LoginUserException");
            status = 400;
            message = MessageManager.getMessage("msg.incorrect.email.password");
            page = PagesManager.PAGE_LOGIN;
        } else if (e instanceof RegistrationException) {
            log.error("RegistrationException");
            status = 400;
            message = e.getMessage();
            page = PagesManager.PAGE_CREATE_USER;
        } else if (e instanceof UnAuthorizedException) {
            log.error("UnAuthorizedException");
            status = 401;
            message = e.getMessage();
            page = PagesManager.PAGE_LOGIN;
        } else if (e instanceof ForbiddenException) {
            log.error("ForbiddenException");
            status = 403;
            message = e.getMessage();
            page = PagesManager.PAGE_INDEX;
        } else if (e instanceof NotFoundException) {
            log.error("NotFoundException");
            status = 404;
            message = MessageManager.getMessage("msg.not.found");
            page = PagesManager.PAGE_404;
        } else if (e instanceof ConnectionPoolException) {
            log.error("ConnectionPoolException");
            status = 404;
            message = e.getMessage();
            page = PagesManager.PAGE_404;
        } else {
            log.error("OtherException");
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