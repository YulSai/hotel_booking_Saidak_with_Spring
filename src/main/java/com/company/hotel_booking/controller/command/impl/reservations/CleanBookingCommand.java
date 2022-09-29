package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class for processing HttpServletRequest "clean_booking"
 */
public class CleanBookingCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("booking");
        return PagesManager.PAGE_BOOKING;
    }
}