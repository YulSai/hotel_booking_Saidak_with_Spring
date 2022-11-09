package com.company.hotel_booking.web.controllers.view;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final PagingUtil pagingUtil;
    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping("/all")
    public String getAllReservations(HttpServletRequest req, HttpSession session, Model model) {
        Pageable pageable = pagingUtil.getPaging(req, "id");
        Page<ReservationDto> reservationsDtoPage = reservationService.findAllPages(pageable);
        List<ReservationDto> reservations = reservationsDtoPage.toList();

        UserDto user = (UserDto) session.getAttribute("user");
        if ("CLIENT".equals(user.getRole().toString())) {
            reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, user.getId());
            reservations = reservationsDtoPage.toList();
        }

        if (reservations.isEmpty()) {
            model.addAttribute("message",
                    messageSource.getMessage("msg.empty", null, LocaleContextHolder.getLocale()));
            return PagesConstants.PAGE_RESERVATIONS;
        }

        pagingUtil.setTotalPages(req, reservationsDtoPage, "reservations/all");
        model.addAttribute("reservations", reservations);
        return PagesConstants.PAGE_RESERVATIONS;
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getReservationById(@PathVariable Long id, Model model) {
        ReservationDto reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return PagesConstants.PAGE_RESERVATION;
    }

    @LogInvocation
    @GetMapping("/create")
    public String createReservation(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("user");
        LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
        LocalDate checkOut = (LocalDate) session.getAttribute("check_out");
        if (user == null) {
            session.setAttribute("message",
                    messageSource.getMessage("msg.login", null, LocaleContextHolder.getLocale()));
            return PagesConstants.PAGE_LOGIN;
        } else {
            @SuppressWarnings("unchecked")
            Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
            ReservationDto created = reservationService.processReservationCreation(booking, user, checkIn, checkOut);
            session.removeAttribute("booking");
            session.setAttribute("message", messageSource
                    .getMessage("msg.reservation.created", null, LocaleContextHolder.getLocale()));
            return "redirect:/reservations/" + created.getId();
        }
    }

    @LogInvocation
    @GetMapping("/update/{id}")
    public String updateReservationForm(@PathVariable Long id, Model model) {
        ReservationDto reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return PagesConstants.PAGE_UPDATE_RESERVATION;
    }

    @LogInvocation
    @PostMapping("/update/{id}")
    public String updateReservation(@PathVariable Long id, @RequestParam String status, HttpSession session) {
        ReservationDto reservation = reservationService.findById(id);
        reservation.setStatus(ReservationDto.StatusDto.valueOf(status.toUpperCase()));

        ReservationDto updated = reservationService.update(reservation);
        session.setAttribute("message", messageSource
                .getMessage("msg.reservation.updated", null, LocaleContextHolder.getLocale()));
        return "redirect:/reservations/" + updated.getId();
    }

    @LogInvocation
    @GetMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Long id, Model model) {
        model.addAttribute("message", messageSource
                .getMessage("msg.delete.not.available", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_ERROR;
    }

    @LogInvocation
    @GetMapping("/cancel_reservation/{id}")
    public String cancelReservation(@PathVariable Long id, HttpSession session) {
        ReservationDto reservation = reservationService.findById(id);
        reservation.setStatus(ReservationDto.StatusDto.REJECTED);
        ReservationDto updated = reservationService.update(reservation);
        session.setAttribute("message",
                messageSource.getMessage("msg.reservations.cancel", null, LocaleContextHolder.getLocale()));
        return "redirect:/reservations/" + updated.getId();
    }

    @LogInvocation
    @PostMapping("/add_booking")
    public String addBooking(HttpSession session, @RequestParam Long roomId, @RequestParam String check_in,
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
        session.setAttribute("booking", booking);
        session.setAttribute("check_in", checkIn);
        session.setAttribute("check_out", checkOut);
        return "redirect:/rooms/rooms_available";
    }

    @LogInvocation
    @GetMapping("/booking")
    public String getBooking(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
        if (booking == null) {
            return PagesConstants.PAGE_BOOKING;
        } else {
            UserDto user = (UserDto) session.getAttribute("user");
            LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
            LocalDate checkOut = (LocalDate) session.getAttribute("check_out");
            ReservationDto processed = reservationService.processBooking(booking, user, checkIn, checkOut);
            model.addAttribute("booking", processed);
            return PagesConstants.PAGE_BOOKING;
        }
    }

    @LogInvocation
    @GetMapping("/clean_booking")
    public String cleanBooking(HttpSession session) {
        session.removeAttribute("booking");
        return PagesConstants.PAGE_BOOKING;
    }

    @LogInvocation
    @GetMapping("/delete_booking/{id}")
    public String deleteBooking(HttpSession session, @PathVariable Long id) {
        LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
        LocalDate checkOut = (LocalDate) session.getAttribute("check_out");

        @SuppressWarnings("unchecked")
        Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
        booking.remove(id);
        session.setAttribute("booking", booking);
        session.setAttribute("check_in", checkIn);
        session.setAttribute("check_out", checkOut);
        return "redirect:/reservations/booking";
    }

    @LogInvocation
    @GetMapping("/user_reservations/{id}")
    public String getAllReservationsByUser(@PathVariable Long id, HttpServletRequest req,
                                           HttpSession session, Model model) {
        Pageable pageable = pagingUtil.getPaging(req, "id");
        Page<ReservationDto> reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, id);
        List<ReservationDto> reservations = reservationsDtoPage.toList();
        if (reservations.isEmpty()) {
            model.addAttribute("message", messageSource
                    .getMessage("msg.reservations.no", null, LocaleContextHolder.getLocale()));
            return PagesConstants.PAGE_RESERVATIONS;
        } else {
            UserDto user = (UserDto) session.getAttribute("user");
            if ("CLIENT".equals(user.getRole().toString())) {
                reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, user.getId());
                reservations = reservationsDtoPage.toList();
            }
            pagingUtil.setTotalPages(req, reservationsDtoPage, "reservations/user_reservations");
            model.addAttribute("reservations", reservations);
            return PagesConstants.PAGE_RESERVATIONS;
        }
    }
}