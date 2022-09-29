package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.service.api.IRoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

/**
 * Class for processing HttpServletRequest "update_room"
 */
public class UpdateRoomCommand implements ICommand {
    private final IRoomService service;

    public UpdateRoomCommand(IRoomService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        RoomDto room = getRoomFromInput(req);
        RoomDto updated = service.update(room);
        req.setAttribute("message", MessageManger.getMessage("msg.room.updated"));
        return "redirect:controller?command=room&id=" + updated.getId();
    }

    private static RoomDto getRoomFromInput(HttpServletRequest req) {
        RoomDto room = new RoomDto();
        room.setId(Long.valueOf(req.getParameter("id")));
        room.setType(RoomDto.RoomTypeDto.valueOf(req.getParameter("type").toUpperCase()));
        room.setPrice(new BigDecimal(req.getParameter("price")));
        room.setStatus(RoomDto.RoomStatusDto.valueOf(req.getParameter("status")));
        room.setCapacity(RoomDto.CapacityDto.valueOf(req.getParameter("capacity")));
        room.setNumber(req.getParameter("room_number"));
        return room;
    }
}