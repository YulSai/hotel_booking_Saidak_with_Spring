package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class for processing HttpServletRequest "rooms_available"
 */
public class RoomsAvailableCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req) {
        return PagesManager.PAGE_ROOMS_AVAILABLE;
    }
}