package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.service.api.IReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class for processing HttpServletRequest "cancel_reservation"
 */
@Controller
@RequiredArgsConstructor
public class CancelReservationCommand implements ICommand {
    private final IReservationService service;

    @Override
    public String execute(HttpServletRequest req) {
        ReservationDto reservation = service.findById(Long.valueOf(req.getParameter("id")));
        reservation.setStatus(ReservationDto.StatusDto.REJECTED);
        ReservationDto updated = service.update(reservation);
        req.setAttribute("message", MessageManger.getMessage("msg.reservations.cancel"));
        return "redirect:controller?command=reservation&id=" + updated.getId();
    }
}