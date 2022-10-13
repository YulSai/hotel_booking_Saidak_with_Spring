package com.company.hotel_booking.controller.command.impl.users;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.ReservationDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Class for processing HttpServletRequest "delete_user"
 */
@Controller
@RequiredArgsConstructor
public class DeleteUserCommand implements ICommand {
    private final UserService service;
    private final ReservationService reservationService;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        List<ReservationDto> reservations = reservationService.findAllByUsers(id);
        for (ReservationDto reservation : reservations) {
            reservation.setStatus(ReservationDto.StatusDto.DELETED);
            reservationService.update(reservation);
        }
        service.delete(id);
        req.setAttribute("message", MessageManager.getMessage("msg.user.deleted"));
        return PagesManager.PAGE_DELETE_USER;
    }
}