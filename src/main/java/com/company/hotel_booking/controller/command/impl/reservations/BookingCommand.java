package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.IReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.Map;

/**
 * Class for processing HttpServletRequest "booking"
 */
public class BookingCommand implements ICommand {
    private final IReservationService reservationService;

    public BookingCommand(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
        if (booking == null) {
            return PagesManager.PAGE_BOOKING;
        } else {
            UserDto user = (UserDto) session.getAttribute("user");
            LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
            LocalDate checkOut = (LocalDate) session.getAttribute("check_out");
            ReservationDto processed = reservationService.processBooking(booking, user, checkIn, checkOut);
            req.setAttribute("booking", processed);
            return PagesManager.PAGE_BOOKING;
        }
    }
}