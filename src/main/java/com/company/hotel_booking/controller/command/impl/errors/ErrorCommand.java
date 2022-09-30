package com.company.hotel_booking.controller.command.impl.errors;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

/**
 * Class for processing HttpServletRequest "error"
 */
@Log4j2
@Controller
@RequiredArgsConstructor
public class ErrorCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req) {
        log.error("Incorrect address");
        return PagesManager.PAGE_ERROR;
    }
}