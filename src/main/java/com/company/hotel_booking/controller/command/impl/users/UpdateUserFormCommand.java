package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class executes the command "update_user_form"
 */
@Controller
@RequiredArgsConstructor
public class UpdateUserFormCommand implements ICommand {
    private final UserService service;

    @Override
    @LogInvocation
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto user = service.findById(id);
        req.setAttribute("user", user);
        return PagesManager.PAGE_UPDATE_USERS;
    }
}