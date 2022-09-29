package com.company.hotel_booking.controller.command.impl.authorizations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class for processing HttpServletRequest "logout"
 */
public class LogoutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
        // req.getSession().invalidate();
        return PagesManager.PAGE_INDEX;
    }
}