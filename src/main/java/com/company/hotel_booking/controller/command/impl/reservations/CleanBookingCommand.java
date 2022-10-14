package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class for processing HttpServletRequest "clean_booking"
 */
@Controller
@RequiredArgsConstructor
public class CleanBookingCommand implements ICommand {
    @Override
    @LogInvocation
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("booking");
        return PagesManager.PAGE_BOOKING;
    }
}