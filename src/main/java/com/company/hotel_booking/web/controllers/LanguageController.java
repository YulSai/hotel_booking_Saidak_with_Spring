package com.company.hotel_booking.web.controllers;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/language")
@RequiredArgsConstructor
public class LanguageController {

    @LogInvocation
    @GetMapping
    public String changeLanguage(HttpServletRequest req) {
        return "redirect:" + req.getHeader("referer");
    }
}
