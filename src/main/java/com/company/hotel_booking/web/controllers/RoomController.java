package com.company.hotel_booking.web.controllers;

import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.constants.PagesConstants;
import com.company.hotel_booking.web.controllers.utils.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final PagingUtil pagingUtil;
    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping("/all")
    public String getAllRooms(Model model, HttpServletRequest req) {
        Pageable pageable = pagingUtil.getPaging(req, "id");
        Page<RoomDto> roomsDtoPage = roomService.findAllPages(pageable);
        List<RoomDto> rooms = roomsDtoPage.toList();
        pagingUtil.setTotalPages(req, roomsDtoPage, "rooms/all");
        model.addAttribute("rooms", rooms);
        return PagesConstants.PAGE_ROOMS;

    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getRoomById(@PathVariable Long id, Model model) {
        RoomDto room = roomService.findById(id);
        model.addAttribute("room", room);
        return PagesConstants.PAGE_ROOM;
    }

    @ModelAttribute
    public RoomDto roomDto() {
        return new RoomDto();
    }

    @LogInvocation
    @GetMapping("/create")
    public String createRoomForm() {
        return PagesConstants.PAGE_CREATE_ROOM;
    }

    @LogInvocation
    @PostMapping("/create")
    public String createRoom(@ModelAttribute @Valid RoomDto roomDto, Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return PagesConstants.PAGE_CREATE_ROOM;
        }
        RoomDto created = roomService.create(roomDto);
        session.setAttribute("message", messageSource
                .getMessage("msg.room.created", null, LocaleContextHolder.getLocale()));
        return "redirect:/rooms/" + created.getId();
    }

    @LogInvocation
    @GetMapping("/update/{id}")
    public String updateRoomForm(@PathVariable Long id, Model model) {
        RoomDto room = roomService.findById(id);
        model.addAttribute("room", room);
        return PagesConstants.PAGE_UPDATE_ROOM;
    }

    @LogInvocation
    @PostMapping("/update/{id}")
    public String updateRoom(@ModelAttribute @Valid RoomDto roomDto, Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return PagesConstants.PAGE_UPDATE_ROOM;
        }
        RoomDto updated = roomService.update(roomDto);
        session.setAttribute("message",
                messageSource.getMessage("msg.room.updated", null, LocaleContextHolder.getLocale()));
        return "redirect:/rooms/" + updated.getId();
    }

    @LogInvocation
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, Model model) {
        model.addAttribute("message", messageSource
                .getMessage("msg.delete.not.available", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_ERROR;
    }

    @LogInvocation
    @GetMapping("/search_available_rooms")
    public String searchAvailableRoomForm() {
        return PagesConstants.PAGE_SEARCH_AVAILABLE_ROOMS;
    }

    @LogInvocation
    @PostMapping("/search_available_rooms")
    public String searchAvailableRoom(HttpSession session, @RequestParam String check_in,
                                      @RequestParam String check_out, @RequestParam String type,
                                      @RequestParam String capacity, Model model) {
        LocalDate checkIn = LocalDate.parse(check_in);
        LocalDate checkOut = LocalDate.parse(check_out);
        if (checkOut.equals(checkIn) | checkOut.isBefore(checkIn)) {
            model.addAttribute("message", messageSource
                    .getMessage("msg.incorrect.date", null, LocaleContextHolder.getLocale()));
            return PagesConstants.PAGE_SEARCH_AVAILABLE_ROOMS;
        } else {
            Long typeId = Room.RoomType.valueOf(type.toUpperCase()).getId();
            Long capacityId = Room.Capacity.valueOf(capacity.toUpperCase()).getId();
            List<RoomDto> roomsAvailable = roomService.findAvailableRooms(typeId, capacityId, checkIn, checkOut);
            if (roomsAvailable.isEmpty()) {
                model.addAttribute("message", messageSource
                        .getMessage("msg.search.no.available.rooms", null, LocaleContextHolder.getLocale()));
            }
            session.setAttribute("rooms_available", roomsAvailable);
            session.setAttribute("check_in", checkIn);
            session.setAttribute("check_out", checkOut);
            return PagesConstants.PAGE_ROOMS_AVAILABLE;
        }
    }

    @LogInvocation
    @PostMapping("/rooms_available")
    public String getAvailableRoom() {
        return PagesConstants.PAGE_ROOMS_AVAILABLE;
    }

    @LogInvocation
    @GetMapping("/js/all")
    public String getAllRoomsJs() {
        return PagesConstants.PAGE_ROOMS_JS;

    }

    @LogInvocation
    @GetMapping("/js/{id}")
    public String getRoomByIdJs(@PathVariable Long id) {
        return PagesConstants.PAGE_ROOM_JS;
    }
}