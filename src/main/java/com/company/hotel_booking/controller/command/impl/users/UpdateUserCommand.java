package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.ConfigurationManager;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.service.api.IUserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.UUID;

/**
 * Class for processing HttpServletRequest "update_user"
 */
public class UpdateUserCommand implements ICommand {
    private final IUserService service;

    public UpdateUserCommand(IUserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        UserDto user = getUserFromInput(req);
        UserDto updated = service.update(user);
        req.setAttribute("message", MessageManger.getMessage("msg.user.updated"));
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
        HttpSession session = req.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        if ("ADMIN".equals(userDto.getRole().toString())) {
            user.setAvatar(req.getParameter("avatar"));
        } else {
            user.setAvatar(getAvatarPath(req));
        }
        return user;
    }

    private String getAvatarPath(HttpServletRequest req) {
        String avatarName;
        try {
            Part part = req.getPart("avatar");
            if (part.getSize() > 0) {
                avatarName = UUID.randomUUID() + "_" + part.getSubmittedFileName();
                String partFile = ConfigurationManager.getInstance().getString("part.avatar");
                part.write(partFile + avatarName);
            } else {
                avatarName = service.findById(Long.parseLong(req.getParameter("id"))).getAvatar();
            }
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        return avatarName;
    }
}