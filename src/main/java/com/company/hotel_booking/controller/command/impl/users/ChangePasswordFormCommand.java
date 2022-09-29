package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.IUserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class executes the command "change_password_form"
 */
public class ChangePasswordFormCommand implements ICommand {
    private final IUserService service;

    public ChangePasswordFormCommand(IUserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto user = service.findById(id);
        req.setAttribute("user", user);
        return PagesManager.PAGE_CHANGE_PASSWORD;
    }
}