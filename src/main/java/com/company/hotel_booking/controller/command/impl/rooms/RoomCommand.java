package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.exceptions.NotFoundException;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import java.util.regex.Pattern;

/**
 * Class for processing HttpServletRequest "room"
 */
@Log4j2
@Controller
@RequiredArgsConstructor
public class RoomCommand implements ICommand {
    private final RoomService service;

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
            RoomDto room = service.findById(id);
            if (room.getId() == null) {
                log.error("Incorrect address entered");
                throw new NotFoundException();
            } else {
                req.setAttribute("room", room);
                log.info("Appeal to room.jsp");
                return PagesManager.PAGE_ROOM;
            }
        } else {
            log.error("Incorrect address entered");
            throw new NotFoundException();
        }
    }
}