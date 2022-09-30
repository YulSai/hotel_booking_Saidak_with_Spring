package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class executes the command "create_user_form"
 */
@Controller
@RequiredArgsConstructor
public class CreateUserFormCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest req) {
        return PagesManager.PAGE_CREATE_USER;
    }
}