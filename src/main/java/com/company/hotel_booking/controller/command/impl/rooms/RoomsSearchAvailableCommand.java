package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

/**
 * Class for processing HttpServletRequest "search_available_rooms"
 */
@Controller
@RequiredArgsConstructor
public class RoomsSearchAvailableCommand implements ICommand {
    private final RoomService roomService;

    @Override
    @LogInvocation
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        LocalDate checkIn = LocalDate.parse(req.getParameter("check_in"));
        LocalDate checkOut = LocalDate.parse(req.getParameter("check_out"));
        if (checkOut.equals(checkIn) | checkOut.isBefore(checkIn)) {
            req.setAttribute("message", MessageManager.getMessage("msg.incorrect.date"));
            return PagesManager.PAGE_SEARCH_AVAILABLE_ROOMS;
        } else {
            Long typeId = Room.RoomType.valueOf(req.getParameter("type").toUpperCase()).getId();
            Long capacityId = Room.Capacity.valueOf(req.getParameter("capacity").toUpperCase()).getId();
            List<RoomDto> roomsAvailable = roomService.findAvailableRooms(typeId, capacityId, checkIn, checkOut);
            if (roomsAvailable.isEmpty()) {
                req.setAttribute("message", MessageManager.getMessage("msg.search.no.available.rooms"));
            }
            session.setAttribute("rooms_available", roomsAvailable);
            session.setAttribute("check_in", checkIn);
            session.setAttribute("check_out", checkOut);
            return PagesManager.PAGE_ROOMS_AVAILABLE;
        }
    }
}