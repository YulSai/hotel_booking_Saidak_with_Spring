package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.exceptions.NotFoundException;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.regex.Pattern;

/**
 * Class for processing HttpServletRequest "room"
 */

@Controller
@RequiredArgsConstructor
public class RoomCommand implements ICommand {
    private final RoomService service;

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
            RoomDto room = service.findById(id);
            if (room.getId() == null) {
                throw new NotFoundException();
            } else {
                req.setAttribute("room", room);
                return PagesManager.PAGE_ROOM;
            }
        } else {
            throw new NotFoundException();
        }
    }
}