package com.company.hotel_booking.web.filters;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.managers.MessageManager;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * Class with filter for localization
 */
@Component
@Order(1)
public class LocaleFilter extends HttpFilter {

    @Override
    @LogInvocation
    protected void doFilter(HttpServletRequest req, HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String language = (String) session.getAttribute("language");
        if (language == null) {
            session.setAttribute("language", "en");
            new MessageManager(Locale.UK);
        } else {
            session.setAttribute("language", language);
            new MessageManager(new Locale(language));
        }
        chain.doFilter(req, res);
    }
}