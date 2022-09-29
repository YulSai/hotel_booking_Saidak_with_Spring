package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class executes the command "create_user_form"
 */
public class CreateUserFormCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest req) {
        return PagesManager.PAGE_CREATE_USER;
    }
}