package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.controller.command.util.PagingUtil;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Class for processing HttpServletRequest "reservations"
 */
@Log4j2
@Controller
@RequiredArgsConstructor
public class UserReservationsCommand implements ICommand {
    private final ReservationService reservationService;
    private final PagingUtil pagingUtil;

    @Override
    public String execute(HttpServletRequest req) {
        Paging paging = pagingUtil.getPaging(req);
        Long id = Long.valueOf(req.getParameter("id"));
        List<ReservationDto> reservations = reservationService.findAllPagesByUsers(paging, id);
        if (reservations.isEmpty()) {
            req.setAttribute("message", MessageManager.getMessage("msg.reservations.no"));
            return PagesManager.PAGE_RESERVATIONS;
        } else {
            HttpSession session = req.getSession();
            UserDto user = (UserDto) session.getAttribute("user");
            if ("CLIENT".equals(user.getRole().toString())) {
                reservations = reservationService.findAllPagesByUsers(paging, user.getId());
            }
            pagingUtil.setTotalPages(req, paging, reservationService);
            req.setAttribute("reservations", reservations);
            return PagesManager.PAGE_RESERVATIONS;
        }
    }
}