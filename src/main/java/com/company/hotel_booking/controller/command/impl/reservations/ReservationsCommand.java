package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.controller.command.util.PagingUtil;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.IReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * Class for processing HttpServletRequest "reservations"
 */
@Log4j2
public class ReservationsCommand implements ICommand {
    private final IReservationService reservationService;
    private final PagingUtil pagingUtil;

    public ReservationsCommand(IReservationService service, PagingUtil pagingUtil) {
        this.reservationService = service;
        this.pagingUtil = pagingUtil;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Paging paging = pagingUtil.getPaging(req);
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        List<ReservationDto> reservations;
        if ("CLIENT".equals(user.getRole().toString())) {
            reservations = reservationService.findAllPagesByUsers(paging, user.getId());
        } else {
            reservations = reservationService.findAllPages(paging);
        }
        if (reservations.isEmpty()) {
            log.error("No reservations yet");
            req.setAttribute("message", MessageManger.getMessage("msg.empty"));
            return PagesManager.PAGE_RESERVATIONS;
        }
        pagingUtil.setTotalPages(req, paging, reservationService);
        req.setAttribute("reservations", reservations);
        return PagesManager.PAGE_RESERVATIONS;
    }
}