package com.company.hotel_booking.web.controllers.rest.rooms;

import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.web.controllers.utils.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomRestController {
    private final RoomService roomService;
    private final PagingUtil pagingUtil;

    @LogInvocation
    @GetMapping
    public Page getAllRooms(HttpServletRequest req) {
        Pageable pageable = pagingUtil.getPagingRest(req, "id");
        return roomService.findAllPages(pageable);

    }

    @LogInvocation
    @GetMapping("/{id}")
    public RoomDto getRoomById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @LogInvocation
    @PostMapping
    public RoomDto createRoom(@ModelAttribute @Valid RoomDto roomDto) {
        return roomService.create(roomDto);
    }

    @LogInvocation
    @PutMapping("/{id}")
    public RoomDto updateRoom(@PathVariable Long id, @ModelAttribute @Valid RoomDto roomDto) {
        roomDto.setId(id);
        return roomService.update(roomDto);
    }

    @LogInvocation
    @PostMapping("/search_available_rooms")
    public List<RoomDto> searchAvailableRoom(@RequestParam String check_in, @RequestParam String check_out,
                                             @RequestParam String type, @RequestParam String capacity) {
        LocalDate checkIn = LocalDate.parse(check_in);
        LocalDate checkOut = LocalDate.parse(check_out);
        Long typeId = Room.RoomType.valueOf(type.toUpperCase()).getId();
        Long capacityId = Room.Capacity.valueOf(capacity.toUpperCase()).getId();
        return roomService.findAvailableRooms(typeId, capacityId, checkIn, checkOut);
    }
}