package com.company.hotel_booking.controller.command.impl.local;

import com.company.hotel_booking.controller.command.api.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Class for processing HttpServletRequest "language_select"
 */
@Controller
@RequiredArgsConstructor
public class LanguageSelectionCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest req) {
        String language = req.getParameter("language");
        HttpSession session = req.getSession();
        session.setAttribute("language", language);

        String url = req.getHeader("referer");
        String REFERER = "http://localhost:8080/hotel_booking/";
        return "redirect:" + url.substring(REFERER.length());
    }
}