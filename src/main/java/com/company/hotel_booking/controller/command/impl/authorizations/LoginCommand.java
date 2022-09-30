package com.company.hotel_booking.controller.command.impl.authorizations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.IUserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

/**
 * Class for processing HttpServletRequest "login"
 */
@Log4j2
@Controller
@RequiredArgsConstructor
public class LoginCommand implements ICommand {
    private final IUserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || ("").equals(email) || password == null || ("").equals(password)) {
            req.setAttribute("massage", MessageManger.getMessage("msg.login.details"));
            return PagesManager.PAGE_LOGIN;
        }
        UserDto userDto = userService.login(email, password);
        HttpSession session = req.getSession();
        session.setAttribute("user", userDto);
        log.info("Appeal to login.jsp.");
        return "redirect:";
    }
}