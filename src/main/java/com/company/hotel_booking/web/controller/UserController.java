package com.company.hotel_booking.web.controller;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.api.UserService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReservationService reservationService;
    private final PagingUtil pagingUtil;

    private final MessageManager messageManager;

    @LogInvocation
    @NotFoundEx
    @GetMapping("/all")
    public String getAllUsers(Model model, HttpServletRequest req) {
        Pageable pageable = pagingUtil.getPaging(req, "lastName");
        Page<UserDto> usersDtoPage = userService.findAllPages(pageable);
        List<UserDto> users = usersDtoPage.toList();
        if (users.size() == 0) {
            throw new NotFoundException();
        } else {
            pagingUtil.setTotalPages(req, usersDtoPage, "users/all");
            model.addAttribute("users", users);
            return PagesManager.PAGE_USERS;
        }
    }

    @LogInvocation
    @NotFoundEx
    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, HttpSession session, Model model) {
        if (id == null) {
            throw new NotFoundException();
        }
        UserDto user = userService.findById(id);
        if (user.getId() == null) {
            throw new NotFoundException();
        } else {
            UserDto userDto = (UserDto) session.getAttribute("user");
            if ("CLIENT".equals(userDto.getRole().toString())) {
                user = userService.findById(userDto.getId());
            }
            model.addAttribute("user", user);
            return PagesManager.PAGE_USER;
        }
    }

    @LogInvocation
    @GetMapping("/create")
    public String createUserForm() {
        return PagesManager.PAGE_CREATE_USER;
    }

    @LogInvocation
    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDto user, HttpSession session, MultipartFile avatarFile) {
        user.setRole(UserDto.RoleDto.CLIENT);
        user.setAvatar(getAvatarPath(avatarFile));
        UserDto created = userService.create(user);
        session.setAttribute("user", created);
        return "redirect:/users/" + created.getId();
    }

    @LogInvocation
    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PagesManager.PAGE_UPDATE_USERS;
    }

    @LogInvocation
    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute UserDto user, MultipartFile avatarFile) {
        if (!avatarFile.isEmpty()) {
            user.setAvatar(getAvatarPath(avatarFile));
        }
        UserDto updated = userService.update(user);
        // model.addAttribute("message", messageManager.getMessage("msg.user.updated"));
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
        model.addAttribute("message", messageManager.getMessage("msg.user.deleted"));
        return PagesManager.PAGE_DELETE_USER;
    }

    @LogInvocation
    @GetMapping("/change_password/{id}")
    public String changePasswordForm(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PagesManager.PAGE_CHANGE_PASSWORD;
    }

    @LogInvocation
    @PostMapping("/change_password/{id}")
    public String changePassword(@ModelAttribute UserDto user) {
        UserDto updated = userService.changePassword(user);
        // model.addAttribute("message", messageManager.getMessage("msg.user.password.change"));
        return "redirect:/users/" + updated.getId();
    }

    @LogInvocation
    @GetMapping("/update_role/{id}")
    public String updateUserRoleForm(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PagesManager.PAGE_UPDATE_USERS_ROLE;
    }

    @LogInvocation
    @PostMapping("/update_role/{id}")
    public String updateUserRole(@ModelAttribute UserDto user) {
        UserDto updated = userService.update(user);
        // model.addAttribute("message", messageManager.getMessage("msg.user.updated"));
        return "redirect:/users/" + updated.getId();
    }

    /**
     * Method writes file and gets path to this file
     *
     * @param avatarFile MultipartFile avatar
     * @return name of file as String
     */
    private String getAvatarPath(MultipartFile avatarFile) {
        String avatarName;
        try {
            if (!avatarFile.isEmpty()) {
                avatarName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
                String location = "avatars/";
                File pathFile = new File(location);
                if(!pathFile.exists()){
                    pathFile.mkdir();
                }
                pathFile = new File(location + avatarName);
                avatarFile.transferTo(pathFile);
            } else {
                avatarName = "defaultAvatar.png";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return avatarName;
    }
}