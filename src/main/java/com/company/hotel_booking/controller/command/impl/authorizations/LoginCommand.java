package com.company.hotel_booking.controller.command.impl.authorizations;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class for processing HttpServletRequest "login"
 */
@Controller
@RequiredArgsConstructor
public class LoginCommand implements ICommand {
    private final UserService userService;

    @Override
    @LogInvocation
    public String execute(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || ("").equals(email) || password == null || ("").equals(password)) {
            req.setAttribute("massage", MessageManager.getMessage("msg.login.details"));
            return PagesManager.PAGE_LOGIN;
        }
        UserDto userDto = userService.login(email, password);
        HttpSession session = req.getSession();
        session.setAttribute("user", userDto);
        return "redirect:";
    }
}