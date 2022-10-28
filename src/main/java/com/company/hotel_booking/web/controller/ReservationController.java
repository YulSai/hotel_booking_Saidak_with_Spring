package com.company.hotel_booking.web.controller;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.utils.managers.PagesManager;
import com.company.hotel_booking.web.controller.utils.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final PagingUtil pagingUtil;
    private final MessageSource messageManager;

    @LogInvocation
    @NotFoundEx
    @GetMapping("/all")
    public String getAllReservations(HttpServletRequest req, HttpSession session, Model model, Locale locale) {
        Pageable pageable = pagingUtil.getPaging(req, "id");
        Page<ReservationDto> reservationsDtoPage = reservationService.findAllPages(pageable);
        List<ReservationDto> reservations = reservationsDtoPage.toList();

        UserDto user = (UserDto) session.getAttribute("user");
        if ("CLIENT".equals(user.getRole().toString())) {
            reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, user.getId());
            reservations = reservationsDtoPage.toList();
        }

        if (reservations.isEmpty()) {
            model.addAttribute("message", messageManager.getMessage("msg.empty", null, locale));
            return PagesManager.PAGE_RESERVATIONS;
        }

        pagingUtil.setTotalPages(req, reservationsDtoPage, "reservations/all");
        model.addAttribute("reservations", reservations);
        return PagesManager.PAGE_RESERVATIONS;
    }

    @LogInvocation
    @NotFoundEx
    @GetMapping("/{id}")
    public String getReservationById(@PathVariable Long id, Model model) {
        ReservationDto reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return PagesManager.PAGE_RESERVATION;
    }

    @LogInvocation
    @GetMapping("/create")
    public String createReservation(HttpSession session, Locale locale) {
        UserDto user = (UserDto) session.getAttribute("user");
        LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
        LocalDate checkOut = (LocalDate) session.getAttribute("check_out");
        if (user == null) {
            session.setAttribute("message", messageManager.getMessage("msg.login", null, locale));
            return PagesManager.PAGE_LOGIN;
        } else {
            @SuppressWarnings("unchecked")
            Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
            ReservationDto created = reservationService.processReservationCreation(booking, user, checkIn, checkOut);
            session.removeAttribute("booking");
            session.setAttribute("message", messageManager
                    .getMessage("msg.reservation.created", null, locale));
            return "redirect:/reservations/" + created.getId();
        }
    }

    @LogInvocation
    @GetMapping("/update/{id}")
    public String updateReservationForm(@PathVariable Long id, Model model) {
        ReservationDto reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return PagesManager.PAGE_UPDATE_RESERVATION;
    }

    @LogInvocation
    @PostMapping("/update/{id}")
    public String updateReservation(@ModelAttribute ReservationDto reservation, @RequestParam String status,
                                    HttpSession session, Locale locale) {
        reservation = reservationService.findById(reservation.getId());
        reservation.setStatus(ReservationDto.StatusDto.valueOf(status.toUpperCase()));
        ReservationDto updated = reservationService.update(reservation);
        session.setAttribute("message", messageManager
                .getMessage("msg.reservation.updated", null, locale));
        return "redirect:/reservations/" + updated.getId();
    }

    @LogInvocation
    @GetMapping("/cancel_reservation/{id}")
    public String cancelReservation(@PathVariable Long id, HttpSession session, Locale locale) {
        ReservationDto reservation = reservationService.findById(id);
        reservation.setStatus(ReservationDto.StatusDto.REJECTED);
        ReservationDto updated = reservationService.update(reservation);
        session.setAttribute("message", messageManager.getMessage("msg.reservations.cancel", null, locale));
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
            return PagesManager.PAGE_BOOKING;
        } else {
            UserDto user = (UserDto) session.getAttribute("user");
            LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
            LocalDate checkOut = (LocalDate) session.getAttribute("check_out");
            ReservationDto processed = reservationService.processBooking(booking, user, checkIn, checkOut);
            model.addAttribute("booking", processed);
            return PagesManager.PAGE_BOOKING;
        }
    }

    @LogInvocation
    @GetMapping("/clean_booking")
    public String cleanBooking(HttpSession session) {
        session.removeAttribute("booking");
        return PagesManager.PAGE_BOOKING;
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
                                           HttpSession session, Model model, Locale locale) {
        Pageable pageable = pagingUtil.getPaging(req, "id");
        Page<ReservationDto> reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, id);
        List<ReservationDto> reservations = reservationsDtoPage.toList();
        if (reservations.isEmpty()) {
            model.addAttribute("message", messageManager
                    .getMessage("msg.reservations.no", null, locale));
            return PagesManager.PAGE_RESERVATIONS;
        } else {
            UserDto user = (UserDto) session.getAttribute("user");
            if ("CLIENT".equals(user.getRole().toString())) {
                reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, user.getId());
                reservations = reservationsDtoPage.toList();
            }
            pagingUtil.setTotalPages(req, reservationsDtoPage, "reservations/user_reservations");
            model.addAttribute("reservations", reservations);
            return PagesManager.PAGE_RESERVATIONS;
        }
    }
}