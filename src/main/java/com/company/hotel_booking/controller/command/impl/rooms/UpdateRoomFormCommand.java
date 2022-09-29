package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.IRoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class executes the command "update_room_form"
 */
public class UpdateRoomFormCommand implements ICommand {

    private final IRoomService service;

    public UpdateRoomFormCommand(IRoomService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        RoomDto room = service.findById(id);
        req.setAttribute("room", room);
        return PagesManager.PAGE_UPDATE_ROOM;
    }
}