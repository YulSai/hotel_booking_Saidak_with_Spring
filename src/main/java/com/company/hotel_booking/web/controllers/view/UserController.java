package com.company.hotel_booking.web.controllers.view;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.api.UserService;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Class for processing HttpServletRequest "users"
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReservationService reservationService;
    private final PagingUtil pagingUtil;
    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping("/all")
    public String getAllUsers(Model model, HttpServletRequest req) {
        Pageable pageable = pagingUtil.getPaging(req, "lastName");
        Page<UserDto> usersDtoPage = userService.findAllPages(pageable);
        List<UserDto> users = usersDtoPage.toList();
        pagingUtil.setTotalPages(req, usersDtoPage, "users/all");
        model.addAttribute("users", users);
        return PagesConstants.PAGE_USERS;
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, HttpSession session, Model model) {
        UserDto user;
        UserDto userDto = (UserDto) session.getAttribute("user");
        if ("CLIENT".equals(userDto.getRole().toString())) {
            user = userService.findById(userDto.getId());
        } else {
            user = userService.findById(id);
        }
        model.addAttribute("user", user);
        return PagesConstants.PAGE_USER;
    }

    @ModelAttribute
    public UserDto userDto() {
        return new UserDto();
    }

    @LogInvocation
    @GetMapping("/create")
    public String createUserForm() {
        return PagesConstants.PAGE_CREATE_USER;
    }

    @LogInvocation
    @PostMapping("/create")
    public String createUser(@ModelAttribute @Valid UserDto userDto, Errors errors, HttpSession session,
                             MultipartFile avatarFile) {
        if (errors.hasErrors()) {
            return PagesConstants.PAGE_CREATE_USER;
        }
        userDto.setRole(UserDto.RoleDto.CLIENT);
        UserDto created = userService.processCreateUser(userDto, avatarFile);
        session.setAttribute("user", created);
        session.setAttribute("message",
                messageSource.getMessage("msg.user.created", null, LocaleContextHolder.getLocale()));
        return "redirect:/users/" + created.getId();
    }

    @LogInvocation
    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PagesConstants.PAGE_UPDATE_USERS;
    }

    @LogInvocation
    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute @Valid UserDto userDto, Errors errors, MultipartFile avatarFile,
                             HttpSession session) {
        if (errors.hasErrors()) {
            return PagesConstants.PAGE_UPDATE_USERS;
        }
        UserDto updated = userService.processUserUpdates(userDto, avatarFile);
        session.setAttribute("message",
                messageSource.getMessage("msg.user.updated", null, LocaleContextHolder.getLocale()));
        return "redirect:/users/" + updated.getId();
    }

    @LogInvocation
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        List<ReservationDto> reservations = reservationService.findAllByUsers(id);
        for (ReservationDto reservation : reservations) {
            reservation.setStatus(ReservationDto.StatusDto.DELETED);
            reservationService.update(reservation);
        }
        userService.delete(userService.findById(id));
        model.addAttribute("message",
                messageSource.getMessage("msg.user.deleted", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_DELETE_USER;
    }

    @LogInvocation
    @GetMapping("/change_password/{id}")
    public String changePasswordForm(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PagesConstants.PAGE_CHANGE_PASSWORD;
    }

    @LogInvocation
    @PostMapping("/change_password/{id}")
    public String changePassword(@ModelAttribute @Valid UserDto userdto, Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return PagesConstants.PAGE_CHANGE_PASSWORD;
        }
        UserDto updated = userService.changePassword(userdto);
        session.setAttribute("message",
                messageSource.getMessage("msg.user.password.change", null, LocaleContextHolder.getLocale()));
        return "redirect:/users/" + updated.getId();
    }

    @LogInvocation
    @GetMapping("/update_role/{id}")
    public String updateUserRoleForm(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PagesConstants.PAGE_UPDATE_USERS_ROLE;
    }

    @LogInvocation
    @PostMapping("/update_role/{id}")
    public String updateUserRole(@ModelAttribute UserDto user, HttpSession session) {
        UserDto updated = userService.update(user);
        session.setAttribute("message",
                messageSource.getMessage("msg.user.updated", null, LocaleContextHolder.getLocale()));
        return "redirect:/users/" + updated.getId();
    }

    @LogInvocation
    @GetMapping("/js/all")
    public String getAllUsersJs() {
        return PagesConstants.PAGE_USERS_JS;
    }

    @LogInvocation
    @GetMapping("/js/{id}")
    public String getUserByIdJs(@PathVariable Long id) {
        return PagesConstants.PAGE_USER_JS;
    }


    @LogInvocation
    @GetMapping("/js/delete/{id}")
    public String deleteUserJs(@PathVariable Long id, Model model) {
        model.addAttribute("message",
                messageSource.getMessage("msg.user.deleted", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_DELETE_USER;
    }
}