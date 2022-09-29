package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.IReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class for processing HttpServletRequest "update_reservation_form"
 */
public class UpdateReservationFormCommand implements ICommand {

    private final IReservationService service;

    public UpdateReservationFormCommand(IReservationService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        ReservationDto reservationDto = service.findById(id);
        req.setAttribute("reservation", reservationDto);
        return PagesManager.PAGE_UPDATE_RESERVATION;
    }
}