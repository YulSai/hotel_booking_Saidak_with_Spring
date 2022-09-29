package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class executes the command "create_room_form"
 */
public class CreateRoomFormCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest req) {
        return PagesManager.PAGE_CREATE_ROOM;
    }
}