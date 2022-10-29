package com.company.hotel_booking.web.controller;

import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.managers.PagesManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final MessageSource messageManager;

    @LogInvocation
    @GetMapping("/login")
    public String loginForm() {
        return PagesManager.PAGE_LOGIN;
    }

    @LogInvocation
    @PostMapping("/login")
    public String login(HttpServletRequest req, @RequestParam String email, @RequestParam String password,
                        HttpSession session, Model model, Locale locale) {
        if (email == null || ("").equals(email) || password == null || ("").equals(password)) {
            model.addAttribute("message", messageManager
                    .getMessage("msg.login.details", null, locale));
            return PagesManager.PAGE_LOGIN;
        }
        UserDto userDto = userService.login(email, password);
        session.setAttribute("user", userDto);
        return PagesManager.PAGE_INDEX;
    }

    @LogInvocation
    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
        // req.getSession().invalidate();
        return PagesManager.PAGE_INDEX;
    }
}
