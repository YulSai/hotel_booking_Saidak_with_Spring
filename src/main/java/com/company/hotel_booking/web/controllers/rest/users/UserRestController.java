package com.company.hotel_booking.web.controllers.rest.users;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.web.controllers.utils.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final ReservationService reservationService;
    private final PagingUtil pagingUtil;


    @LogInvocation
    @GetMapping
    public Page<UserDto> getAllUsersJs(HttpServletRequest req) {
        Pageable pageable = pagingUtil.getPagingRest(req, "lastName");
        return userService.findAllPages(pageable);
    }

    @LogInvocation
    @GetMapping("/{id}")
    public UserDto getUserByIdJs(@PathVariable Long id, HttpSession session) {
        UserDto user;
        UserDto userDto = (UserDto) session.getAttribute("user");
        if ("CLIENT".equals(userDto.getRole().toString())) {
            user = userService.findById(userDto.getId());
        } else {
            user = userService.findById(id);
        }
        return user;
    }

    @LogInvocation
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@ModelAttribute @Valid UserDto userDto, HttpSession session, MultipartFile avatarFile) {
        userDto.setRole(UserDto.RoleDto.CLIENT);
        UserDto created = userService.processCreateUser(userDto, avatarFile);
        session.setAttribute("user", created);
        return created;
    }

    @LogInvocation
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDto updateUser(@PathVariable Long id, @ModelAttribute @Valid UserDto userDto, MultipartFile avatarFile) {
        userDto.setId(id);
        return userService.processUserUpdates(userDto, avatarFile);
    }

    @LogInvocation
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        List<ReservationDto> reservations = reservationService.findAllByUsers(id);
        for (ReservationDto reservation : reservations) {
            reservation.setStatus(ReservationDto.StatusDto.DELETED);
            reservationService.update(reservation);
        }
        userService.delete(userService.findById(id));
    }
}