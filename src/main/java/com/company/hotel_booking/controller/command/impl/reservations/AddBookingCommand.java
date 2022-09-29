package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for processing HttpServletRequest "add_booking"
 */
public class AddBookingCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long roomId = Long.valueOf(req.getParameter("room_id"));
        LocalDate checkIn = LocalDate.parse(req.getParameter("check_in"));
        LocalDate checkOut = LocalDate.parse(req.getParameter("check_out"));

        @SuppressWarnings("unchecked")
        Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
        if (booking == null) {
            booking = new HashMap<>();
        }
        Long quantity = ChronoUnit.DAYS.between(checkIn, checkOut);
        booking.put(roomId, quantity);
        session.setAttribute("booking", booking);
        session.setAttribute("check_in", checkIn);
        session.setAttribute("check_out", checkOut);
        return "redirect:controller?command=rooms_available";
    }
}