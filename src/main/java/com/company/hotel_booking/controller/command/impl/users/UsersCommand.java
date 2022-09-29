package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.controller.command.util.PagingUtil;
import com.company.hotel_booking.exceptions.NotFoundException;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.IUserService;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * Class for processing HttpServletRequest "users"
 */
@Log4j2
public class UsersCommand implements ICommand {
    private final IUserService userService;
    private final PagingUtil pagingUtil;

    public UsersCommand(IUserService service, PagingUtil pagingUtil) {
        this.userService = service;
        this.pagingUtil = pagingUtil;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Paging paging = pagingUtil.getPaging(req);
        List<UserDto> users = userService.findAllPages(paging);
        if (users.size() == 0) {
            log.error("Incorrect address entered");
            throw new NotFoundException();
        } else {
            pagingUtil.setTotalPages(req, paging, userService);
            req.setAttribute("users", users);
            return PagesManager.PAGE_USERS;
        }
    }
}