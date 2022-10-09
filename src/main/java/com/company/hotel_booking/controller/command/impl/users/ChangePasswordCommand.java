package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.IUserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class for processing HttpServletRequest "change_password"
 */
@Controller
@RequiredArgsConstructor
public class ChangePasswordCommand implements ICommand {
    private final IUserService service;

    @Override
    public String execute(HttpServletRequest req) {
        UserDto user = getUserFromInput(req);
        UserDto updated = service.changePassword(user);
        req.setAttribute("message", MessageManager.getMessage("msg.user.password.change"));
        return "redirect:controller?command=user&id=" + updated.getId();
    }

    private UserDto getUserFromInput(HttpServletRequest req) {
        UserDto user = new UserDto();
        user.setId(Long.parseLong(req.getParameter("id")));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setFirstName(req.getParameter("first_name"));
        user.setLastName(req.getParameter("last_name"));
        user.setPhoneNumber(req.getParameter("phone_number"));
        user.setRole(UserDto.RoleDto.valueOf(req.getParameter("role").toUpperCase()));
        user.setAvatar(req.getParameter("avatar"));
        return user;
    }
}