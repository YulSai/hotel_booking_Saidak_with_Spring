package com.company.hotel_booking.web.controller;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/language")
@RequiredArgsConstructor
public class LanguageController {

    @LogInvocation
    @GetMapping("/en")
    public String selectLanguageEnglish(HttpServletRequest req, HttpSession session) {
        session.setAttribute("language", "en");
        return "redirect:" + req.getHeader("referer");
    }

    @LogInvocation
    @GetMapping("/ru")
    public String selectLanguageRussian(HttpServletRequest req, HttpSession session) {
        session.setAttribute("language", "ru");
        return "redirect:" + req.getHeader("referer");
    }
}
