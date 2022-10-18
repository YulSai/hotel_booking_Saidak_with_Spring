package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.controller.command.util.PagingUtil;
import com.company.hotel_booking.exceptions.NotFoundException;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Class for processing HttpServletRequest "users"
 */
@Controller
@RequiredArgsConstructor
public class UsersCommand implements ICommand {
    private final UserService userService;
    private final PagingUtil pagingUtil;

    @Override
    @LogInvocation
    @NotFoundEx
    public String execute(HttpServletRequest req) {
        Pageable pageable = pagingUtil.getPaging(req, "lastName");
        Page<UserDto> usersDtoPage =  userService.findAllPages(pageable);
        List<UserDto> users = usersDtoPage.toList();
        if (users.size() == 0) {
            throw new NotFoundException();
        } else {
            pagingUtil.setTotalPages(req, usersDtoPage);
            req.setAttribute("users", users);
            return PagesManager.PAGE_USERS;
        }
    }
}