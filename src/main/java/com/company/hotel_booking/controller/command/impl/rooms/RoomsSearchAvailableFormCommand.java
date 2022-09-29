package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class for processing HttpServletRequest "search_available_rooms_form"
 */
public class RoomsSearchAvailableFormCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req) {
        return PagesManager.PAGE_SEARCH_AVAILABLE_ROOMS;
    }
}