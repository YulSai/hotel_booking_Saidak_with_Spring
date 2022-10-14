package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.exceptions.NotFoundException;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.regex.Pattern;

/**
 * Class for processing HttpServletRequest "user"
 */
@Controller
@RequiredArgsConstructor
public class UserCommand implements ICommand {
    private final UserService service;

    @Override
    @LogInvocation
    @NotFoundEx
    public String execute(HttpServletRequest req) {
        String regexId = "\\d*";
        String argument = req.getParameter("id");
        if (argument == null) {
            throw new NotFoundException();
        } else if (argument.equals("")) {
            throw new NotFoundException();
        } else if (Pattern.matches(regexId, argument)) {
            Long id = Long.parseLong(req.getParameter("id"));
            UserDto user = service.findById(id);
            if (user.getId() == null) {
                throw new NotFoundException();
            } else {
                HttpSession session = req.getSession();
                UserDto userDto = (UserDto) session.getAttribute("user");
                if ("CLIENT".equals(userDto.getRole().toString())) {
                    user = service.findById(userDto.getId());
                }
                req.setAttribute("user", user);
                return PagesManager.PAGE_USER;
            }
        } else {
            throw new NotFoundException();
        }
    }
}