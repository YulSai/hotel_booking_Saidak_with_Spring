package com.company.hotel_booking.controller.command.impl.rooms;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.controller.command.util.PagingUtil;
import com.company.hotel_booking.exceptions.NotFoundException;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.managers.PagesManager;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Class for processing HttpServletRequest "rooms"
 */
@Controller
@RequiredArgsConstructor
public class RoomsCommand implements ICommand {
    private final RoomService roomService;
    private final PagingUtil pagingUtil;

    @Override
    @LogInvocation
    @NotFoundEx
    public String execute(HttpServletRequest req) {
        Pageable pageable = pagingUtil.getPaging(req, "id");
        Page<RoomDto> roomsDtoPage = roomService.findAllPages(pageable);
        List<RoomDto> rooms = roomsDtoPage.toList();
        if (rooms.size() == 0) {
            throw new NotFoundException();
        } else {
            pagingUtil.setTotalPages(req, roomsDtoPage);
            req.setAttribute("rooms", rooms);
            return PagesManager.PAGE_ROOMS;
        }
    }
}
