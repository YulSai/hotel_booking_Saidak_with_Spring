package com.company.hotel_booking.web.controllers.rest.reservations;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.aspects.logging.annotations.NotFoundEx;
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
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationRestController {
    private final ReservationService reservationService;
    private final PagingUtil pagingUtil;

    @LogInvocation
    @NotFoundEx
    @GetMapping
    public Page getAllReservations(HttpServletRequest req, HttpSession session) {
        Pageable pageable = pagingUtil.getPagingRest(req, "id");
        Page<ReservationDto> reservationsDtoPage = reservationService.findAllPages(pageable);
        UserDto user = (UserDto) session.getAttribute("user");
        if ("CLIENT".equals(user.getRole().toString())) {
            reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, user.getId());
        }
        return reservationsDtoPage;
    }

    @LogInvocation
    @NotFoundEx
    @GetMapping("/{id}")
    public ReservationDto getReservationById(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    @LogInvocation
    @PostMapping
    public ReservationDto createReservation(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("user");
        LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
        LocalDate checkOut = (LocalDate) session.getAttribute("check_out");
        @SuppressWarnings("unchecked")
        Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
        return reservationService.processReservationCreation(booking, user, checkIn, checkOut);
    }

    @LogInvocation
    @PutMapping("/{id}")
    public ReservationDto updateReservation(@PathVariable Long id, @ModelAttribute ReservationDto reservation,
                                            @RequestParam String status) {
        reservation.setId(id);
        reservation.setStatus(ReservationDto.StatusDto.valueOf(status.toUpperCase()));
        return reservationService.update(reservation);
    }

    @LogInvocation
    @PutMapping("/cancel_reservation/{id}")
    public ReservationDto cancelReservation(@PathVariable Long id, @ModelAttribute ReservationDto reservation) {
        reservation.setId(id);
        reservation.setStatus(ReservationDto.StatusDto.REJECTED);
        return reservationService.update(reservation);
    }

    @LogInvocation
    @PostMapping("/add_booking")
    public Map<Long, Long> addBooking(HttpSession session, @RequestParam Long roomId, @RequestParam String check_in,
                                      @RequestParam String check_out) {
        LocalDate checkIn = LocalDate.parse(check_in);
        LocalDate checkOut = LocalDate.parse(check_out);

        @SuppressWarnings("unchecked")
        Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
        if (booking == null) {
            booking = new HashMap<>();
        }
        Long quantity = ChronoUnit.DAYS.between(checkIn, checkOut);
        booking.put(roomId, quantity);
        return booking;
    }

    @LogInvocation
    @GetMapping("/booking")
    public ReservationDto getBooking(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
        UserDto user = (UserDto) session.getAttribute("user");
        LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
        LocalDate checkOut = (LocalDate) session.getAttribute("check_out");
        return reservationService.processBooking(booking, user, checkIn, checkOut);
    }

    @LogInvocation
    @GetMapping("/clean_booking")
    public void cleanBooking(HttpSession session) {
        session.removeAttribute("booking");
    }

    @LogInvocation
    @GetMapping("/delete_booking/{id}")
    public void deleteBooking(HttpSession session, @PathVariable Long id) {
        @SuppressWarnings("unchecked")
        Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
        booking.remove(id);
    }

    @LogInvocation
    @GetMapping("/user_reservations/{id}")
    public Page<ReservationDto> getAllReservationsByUser(@PathVariable Long id, HttpServletRequest req,
                                                         HttpSession session) {
        Pageable pageable = pagingUtil.getPagingRest(req, "id");
        Page<ReservationDto> reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, id);
        UserDto user = (UserDto) session.getAttribute("user");
        if ("CLIENT".equals(user.getRole().toString())) {
            reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, user.getId());
        }
        return reservationsDtoPage;
    }
}