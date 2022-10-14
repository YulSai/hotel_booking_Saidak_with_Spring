package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class for processing HttpServletRequest "search_available_rooms_form"
 */
@Controller
@RequiredArgsConstructor
public class RoomsSearchAvailableFormCommand implements ICommand {
    @Override
    @LogInvocation
    public String execute(HttpServletRequest req) {
        return PagesManager.PAGE_SEARCH_AVAILABLE_ROOMS;
    }
}