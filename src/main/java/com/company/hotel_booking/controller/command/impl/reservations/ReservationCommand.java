package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.exceptions.NotFoundException;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import java.util.regex.Pattern;

/**
 * Class for processing HttpServletRequest "reservation"
 */
@Controller
@RequiredArgsConstructor
@Log4j2
public class ReservationCommand implements ICommand {
    private final ReservationService service;

    @Override
    public String execute(HttpServletRequest req) {
        String regexId = "\\d*";
        String argument = req.getParameter("id");
        if (argument == null) {
            log.error("Incorrect address entered");
            throw new NotFoundException();
        } else if (argument.equals("")) {
            log.error("Incorrect address entered");
            throw new NotFoundException();
        } else if (Pattern.matches(regexId, argument)) {
            Long id = Long.parseLong(req.getParameter("id"));
            ReservationDto reservation = service.findById(id);
            if (reservation.getId() == null) {
                log.error("Incorrect address entered");
                throw new NotFoundException();
            } else {
                req.setAttribute("reservation", reservation);
                log.info("Appeal to reservation.jsp");
                return PagesManager.PAGE_RESERVATION;
            }
        } else {
            log.error("Incorrect address entered");
            throw new NotFoundException();
        }
    }
}
