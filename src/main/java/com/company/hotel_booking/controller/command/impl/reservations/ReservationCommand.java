package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.exceptions.NotFoundException;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.regex.Pattern;

/**
 * Class for processing HttpServletRequest "reservation"
 */
@Controller
@RequiredArgsConstructor
public class ReservationCommand implements ICommand {
    private final ReservationService service;

    @Override
    @LogInvocation
    @NotFoundEx
    public String execute(HttpServletRequest req) {
        String regexId = "\\d*";
        String argument = req.getParameter("id");
        if (argument == null) {
            throw new NotFoundException();
        } else if (argument.equals("")) {
            throw new NotFoundException();
        } else if (Pattern.matches(regexId, argument)) {
            Long id = Long.parseLong(req.getParameter("id"));
            ReservationDto reservation = service.findById(id);
            if (reservation.getId() == null) {
                throw new NotFoundException();
            } else {
                req.setAttribute("reservation", reservation);
                return PagesManager.PAGE_RESERVATION;
            }
        } else {
            throw new NotFoundException();
        }
    }
}
