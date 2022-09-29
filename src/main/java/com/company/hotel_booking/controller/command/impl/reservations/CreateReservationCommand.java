package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.IReservationInfoService;
import com.company.hotel_booking.service.api.IReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.Map;

/**
 * Class for processing HttpServletRequest "create_reservation"
 */
public class CreateReservationCommand implements ICommand {
    private final IReservationService reservationService;
    private final IReservationInfoService reservationInfoService;

    public CreateReservationCommand(IReservationService reservationService,
                                    IReservationInfoService reservationInfoService) {
        this.reservationService = reservationService;
        this.reservationInfoService = reservationInfoService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
        LocalDate checkOut = (LocalDate) session.getAttribute("check_out");
        if (user == null) {
            req.setAttribute("message", MessageManger.getMessage("msg.login"));
            return PagesManager.PAGE_LOGIN;
        } else {
            @SuppressWarnings("unchecked")
            Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
            ReservationDto processed = reservationService.processBooking(booking, user, checkIn, checkOut);
            ReservationDto created = reservationService.create(processed);
            reservationInfoService.processBookingInfo(booking, checkIn, checkOut, created);
            req.getSession().removeAttribute("booking");
            req.setAttribute("message", MessageManger.getMessage("msg.reservation.created"));
            return "redirect:controller?command=reservation&id=" + created.getId();
        }
    }
}