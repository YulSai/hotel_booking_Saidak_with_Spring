package com.company.hotel_booking.web.controller;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.utils.exceptions.NotFoundException;
import com.company.hotel_booking.utils.managers.MessageManager;
import com.company.hotel_booking.utils.managers.PagesManager;
import com.company.hotel_booking.web.controller.util.PagingUtil;
import lombok.RequiredArgsConstructor;
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
import java.util.Map;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final PagingUtil pagingUtil;
    private final MessageManager messageManager;

    @LogInvocation
    @NotFoundEx
    @GetMapping("/all")
    public String getAllReservations(Model model, HttpServletRequest req) {
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

        pagingUtil.setTotalPages(req, reservationsDtoPage, "reservations/all");
        model.addAttribute("reservations", reservations);
        return PagesManager.PAGE_RESERVATIONS;
    }

    @LogInvocation
    @NotFoundEx
    @GetMapping("/{id}")
    public String getReservationById(@PathVariable Long id, Model model) {
        if (id == null) {
            throw new NotFoundException();
        }
        ReservationDto reservation = reservationService.findById(id);
        if (reservation.getId() == null) {
            throw new NotFoundException();
        } else {
            model.addAttribute("reservation", reservation);
            return PagesManager.PAGE_RESERVATION;
        }
    }

    @LogInvocation
    @GetMapping("/create")
    public String createReservation(HttpSession session, Model model) {
        UserDto user = (UserDto) session.getAttribute("user");
        LocalDate checkIn = (LocalDate) session.getAttribute("check_in");
        LocalDate checkOut = (LocalDate) session.getAttribute("check_out");
        if (user == null) {
            model.addAttribute("message", MessageManager.getMessage("msg.login"));
            return PagesManager.PAGE_LOGIN;
        } else {
            @SuppressWarnings("unchecked")
            Map<Long, Long> booking = (Map<Long, Long>) session.getAttribute("booking");
            ReservationDto processed = reservationService.processBooking(booking, user, checkIn, checkOut);
            ReservationDto created = reservationService.create(processed);
            session.removeAttribute("booking");
            // model.addAttribute("message", MessageManager.getMessage("msg.reservation.created"));
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
                                    Model model) {
        reservation = reservationService.findById(reservation.getId());
        reservation.setStatus(ReservationDto.StatusDto.valueOf(status.toUpperCase()));
        ReservationDto updated = reservationService.update(reservation);
        model.addAttribute("message", MessageManager.getMessage("msg.reservation.updated"));
        return "redirect:/reservations/" + updated.getId();
    }

    @LogInvocation
    @GetMapping("/cancel_reservation/{id}")
    public String cancelReservation(@PathVariable Long id) {
        ReservationDto reservation = reservationService.findById(id);
        reservation.setStatus(ReservationDto.StatusDto.REJECTED);
        ReservationDto updated = reservationService.update(reservation);
        //  model.addAttribute("message", MessageManager.getMessage("msg.reservations.cancel"));
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
    public String getAllReservationsByUser(@PathVariable Long id, Model model, HttpServletRequest req,
                                           HttpSession session) {
        Pageable pageable = pagingUtil.getPaging(req, "id");
        Page<ReservationDto> reservationsDtoPage = reservationService.findAllPagesByUsers(pageable, id);
        List<ReservationDto> reservations = reservationsDtoPage.toList();
        if (reservations.isEmpty()) {
            model.addAttribute("message", MessageManager.getMessage("msg.reservations.no"));
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