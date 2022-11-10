package com.company.hotel_booking.web.controllers.view;

import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.constants.PagesConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class for processing HttpServletRequest "login"
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping("/login")
    public String loginForm() {
        return PagesConstants.PAGE_LOGIN;
    }

    @LogInvocation
    @PostMapping("/login")
    public String login(HttpServletRequest req, @RequestParam String username, @RequestParam String password,
                        HttpSession session, Model model) {
        if (username == null || ("").equals(username) || password == null || ("").equals(password)) {
            model.addAttribute("message", messageSource
                    .getMessage("msg.login.details", null, LocaleContextHolder.getLocale()));
            return PagesConstants.PAGE_LOGIN;
        }
        UserDto userDto = userService.login(username, password);
        session.setAttribute("user", userDto);
        return PagesConstants.PAGE_INDEX;
    }

    @LogInvocation
    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
        return PagesConstants.PAGE_INDEX;
    }
}
