package com.company.hotel_booking.web.controllers;

import com.company.hotel_booking.utils.constants.PagesConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return PagesConstants.PAGE_INDEX;
    }
}
