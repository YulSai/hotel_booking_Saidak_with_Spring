package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.IUserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

/**
 * Class for processing HttpServletRequest "create_user"
 */
@Controller
@RequiredArgsConstructor
public class CreateUserCommand implements ICommand {
    private final IUserService service;

    @Override
    public String execute(HttpServletRequest req) {
        UserDto user = getUserFromInput(req);
        UserDto created = service.create(user);
        HttpSession session = req.getSession();
        session.setAttribute("user", created);
        req.setAttribute("message", MessageManager.getMessage("msg.user.created"));
        return "redirect:controller?command=user&id=" + created.getId();
    }

    /**
     * Method gets information about user
     *
     * @param req HttpServletRequest
     * @return UserDto
     */
    private UserDto getUserFromInput(HttpServletRequest req) {
        UserDto user = new UserDto();
        user.setFirstName(req.getParameter("first_name"));
        user.setLastName(req.getParameter("last_name"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setPhoneNumber(req.getParameter("phone_number"));
        user.setRole(UserDto.RoleDto.CLIENT);
        user.setAvatar(getAvatarPath(req));
        return user;
    }

    /**
     * Method writes file and gets path to this file
     *
     * @param req HttpServletRequest
     * @return path to file as String
     */
    private String getAvatarPath(HttpServletRequest req) {
        String avatarName;
        try {
            Part part = req.getPart("avatar");
            if (part.getSize() > 0) {
                try (InputStream in = getClass().getResourceAsStream("/application.properties")) {
                    avatarName = UUID.randomUUID() + "_" + part.getSubmittedFileName();
                    Properties properties = new Properties();
                    properties.load(in);
                    String partFile = properties.getProperty("part.avatar");
                    part.write(partFile + avatarName);
                }
            } else {
                avatarName = "defaultAvatar.png";
            }
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        return avatarName;
    }
}