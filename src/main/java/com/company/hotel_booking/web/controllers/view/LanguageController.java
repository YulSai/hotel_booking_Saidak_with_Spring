package com.company.hotel_booking.web.controllers.view;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Class for processing HttpServletRequest "language"
 */
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
