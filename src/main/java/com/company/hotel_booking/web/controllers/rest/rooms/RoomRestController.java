package com.company.hotel_booking.web.controllers.rest.rooms;

import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.exceptions.rest.MethodNotAllowedException;
import com.company.hotel_booking.utils.exceptions.rest.ValidationException;
import com.company.hotel_booking.web.controllers.utils.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Class for processing HttpServletRequest "api/rooms"
 */
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomRestController {
    private final RoomService roomService;
    private final PagingUtil pagingUtil;
    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping
    public Page<RoomDto> getAllRoomsJs(HttpServletRequest req) {
        Pageable pageable = pagingUtil.getPagingRest(req, "id");
        return roomService.findAllPages(pageable);

    }

    @LogInvocation
    @GetMapping("/{id}")
    public RoomDto getRoomByIdJs(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @LogInvocation
    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@RequestBody @Valid RoomDto roomDto, Errors errors) {
        checkErrors(errors);
        RoomDto created = roomService.create(roomDto);
        return buildResponseCreated(created);
    }

    @LogInvocation
    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id, @RequestBody @Valid RoomDto roomDto, Errors errors) {
        checkErrors(errors);
        roomDto.setId(id);
        RoomDto updated = roomService.update(roomDto);
        return buildResponseCreated(updated);
    }

    @LogInvocation
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteReservations(@PathVariable Long id) {
        throw new MethodNotAllowedException (messageSource.
                getMessage("msg.delete.not.available", null, LocaleContextHolder.getLocale()));
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

    /**
     * Method checks for validation errors on the object
     * @param errors validation errors
     */
    private void checkErrors(Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

    /**
     * Method build ResponseEntity object
     * @param room object RoomDto
     * @return ResponseEntity RoomDto object
     */
    private ResponseEntity<RoomDto> buildResponseCreated(RoomDto room) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(getLocation(room))
                .body(room);
    }

    /**
     * Method gets url for object
     * @param room object RoomDto
     * @return url
     */
    private URI getLocation(RoomDto room) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/js/{id}")
                .buildAndExpand(room.getId())
                .toUri();
    }
}