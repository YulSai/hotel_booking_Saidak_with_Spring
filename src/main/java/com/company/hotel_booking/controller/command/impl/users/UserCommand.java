package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.exceptions.NotFoundException;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.IUserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import java.util.regex.Pattern;

/**
 * Class for processing HttpServletRequest "user"
 */
@Log4j2
@Controller
@RequiredArgsConstructor
public class UserCommand implements ICommand {
    private final IUserService service;

    @Override
    public String execute(HttpServletRequest req) {
        String regexId = "\\d*";
        String argument = req.getParameter("id");
        if (argument == null) {
            log.error("Incorrect address entered");
            throw new NotFoundException();
        } else if (argument.equals("")) {
            log.error("Incorrect address entered");
            throw new NotFoundException();
        } else if (Pattern.matches(regexId, argument)) {
            Long id = Long.parseLong(req.getParameter("id"));
            UserDto user = service.findById(id);
            if (user.getId() == null) {
                log.error("Incorrect address entered");
                throw new NotFoundException();
            } else {
                HttpSession session = req.getSession();
                UserDto userDto = (UserDto) session.getAttribute("user");
                if ("CLIENT".equals(userDto.getRole().toString())) {
                    user = service.findById(userDto.getId());
                }
                req.setAttribute("user", user);
                log.info("Appeal to userInfo.jsp.");
                return PagesManager.PAGE_USER;
            }
        } else {
            log.error("Incorrect address entered");
            throw new NotFoundException();
        }
    }
}