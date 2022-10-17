package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class executes the command "update_room_form"
 */
@Controller
@RequiredArgsConstructor
public class UpdateRoomFormCommand implements ICommand {

    private final RoomService service;

    @Override
    @LogInvocation
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        RoomDto room = service.findById(id);
        req.setAttribute("room", room);
        return PagesManager.PAGE_UPDATE_ROOM;
    }
}