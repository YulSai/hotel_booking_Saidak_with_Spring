package com.company.hotel_booking.web.controllers.rest;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.exceptions.rest.ValidationException;
import com.company.hotel_booking.web.controllers.utils.PagingUtil;
import com.company.hotel_booking.web.controllers.utils.UserRoleUtil;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Class for processing HttpServletRequest "api/users"
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final ReservationService reservationService;
    private final PagingUtil pagingUtil;
    private final UserRoleUtil userRoleUtil;


    @LogInvocation
    @GetMapping
    public Page<UserDto> getAllUsersJs(HttpServletRequest req, HttpSession session) {
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
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto, Errors errors, HttpSession session,
                                              MultipartFile avatarFile) {
        checkErrors(errors);
        userDto.setRole(UserDto.RoleDto.CLIENT);
        UserDto created = userService.processCreateUser(userDto, avatarFile);
        session.setAttribute("user", created);
        return buildResponseCreated(created);
    }

    @LogInvocation
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto userDto, Errors errors,
                                              MultipartFile avatarFile, HttpSession session) {
        userRoleUtil.checkUserRoleAdmin(session);
        checkErrors(errors);
        userDto.setId(id);
        UserDto updated = userService.processUserUpdates(userDto, avatarFile);
        return buildResponseCreated(updated);

    }

    @LogInvocation
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id, HttpSession session) {
        userRoleUtil.checkUserRoleClient(session);
        List<ReservationDto> reservations = reservationService.findAllByUsers(id);
        for (ReservationDto reservation : reservations) {
            reservation.setStatus(ReservationDto.StatusDto.DELETED);
            reservationService.update(reservation);
        }
        userService.delete(userService.findById(id));
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
     * @param user object UserDto
     * @return ResponseEntity User object
     */
    private ResponseEntity<UserDto> buildResponseCreated(UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(getLocation(user))
                .body(user);
    }

    /**
     * Method gets url for object
     * @param user object UserDto
     * @return url
     */
    private URI getLocation(UserDto user) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/js/{id}")
                .buildAndExpand(user.getId())
                .toUri();
    }
}