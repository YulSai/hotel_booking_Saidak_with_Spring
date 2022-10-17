package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class for processing HttpServletRequest "rooms_available"
 */
@Controller
@RequiredArgsConstructor
public class RoomsAvailableCommand implements ICommand {
    @Override
    @LogInvocation
    public String execute(HttpServletRequest req) {
        return PagesManager.PAGE_ROOMS_AVAILABLE;
    }
}