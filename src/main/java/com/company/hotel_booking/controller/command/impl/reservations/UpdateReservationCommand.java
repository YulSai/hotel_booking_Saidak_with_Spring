package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.IReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class for processing HttpServletRequest "update_reservation"
 */
@Controller
@RequiredArgsConstructor
public class UpdateReservationCommand implements ICommand {
    private final IReservationService service;

    @Override
    public String execute(HttpServletRequest req) {
        ReservationDto reservation = service.findById(Long.valueOf(req.getParameter("id")));
        String status = req.getParameter("status").toUpperCase();
        reservation.setStatus(ReservationDto.StatusDto.valueOf(status));
        ReservationDto updated = service.update(reservation);
        req.setAttribute("message", MessageManager.getMessage("msg.reservation.updated"));
        return "redirect:controller?command=reservation&id=" + updated.getId();
    }
}