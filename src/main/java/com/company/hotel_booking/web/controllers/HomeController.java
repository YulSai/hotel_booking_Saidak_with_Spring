package com.company.hotel_booking.web.controllers;

import com.company.hotel_booking.utils.managers.PagesManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return PagesManager.PAGE_INDEX;
    }
}
