package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Map;

/**
 * Class for processing HttpServletRequest "create_reservation"
 */
@Controller
@RequiredArgsConstructor
public class CreateReservationCommand implements ICommand {
    private final ReservationService reservationService;

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
        LocalDate checkOut = (LocalDate) session.getAttribute("check_out");
        if (user == null) {
            req.setAttribute("message", MessageManager.getMessage("msg.login"));
            return PagesManager.PAGE_LOGIN;
        } else {
            @SuppressWarnings("unchecked")
            Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
            ReservationDto processed = reservationService.processBooking(booking, user, checkIn, checkOut);
            ReservationDto created = reservationService.create(processed);
            req.getSession().removeAttribute("booking");
            req.setAttribute("message", MessageManager.getMessage("msg.reservation.created"));
            return "redirect:controller?command=reservation&id=" + created.getId();
        }
    }
}