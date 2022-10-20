package com.company.hotel_booking.controller.command.impl.reservations;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.controller.command.util.PagingUtil;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Class for processing HttpServletRequest "reservations"
 */
@Controller
@RequiredArgsConstructor
public class ReservationsCommand implements ICommand {
    private final ReservationService reservationService;
    private final PagingUtil pagingUtil;

    @Override
    @LogInvocation
    public String execute(HttpServletRequest req) {
        Pageable pageable = pagingUtil.getPaging(req, "id");
        Page<ReservationDto> reservationsDtoPage = reservationService.findAllPages(pageable);
        List<ReservationDto> reservations = reservationsDtoPage.toList();

        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        if ("CLIENT".equals(user.getRole().toString())) {
            reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, user.getId());
            reservations = reservationsDtoPage.toList();
        }

        if (reservations.isEmpty()) {
            req.setAttribute("message", MessageManager.getMessage("msg.empty"));
            return PagesManager.PAGE_RESERVATIONS;
        }

        pagingUtil.setTotalPages(req, reservationsDtoPage);
        req.setAttribute("reservations", reservations);
        return PagesManager.PAGE_RESERVATIONS;
    }
}