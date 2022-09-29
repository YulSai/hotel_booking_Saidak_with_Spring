package com.company.hotel_booking.controller.command.impl.authorizations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

/**
 * Class for processing HttpServletRequest "login_form"
 */
@Log4j2
public class LoginFormCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req) {
        return PagesManager.PAGE_LOGIN;
    }
}
