package com.company.hotel_booking.web.controller;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.exceptions.LoginUserException;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import com.company.hotel_booking.utils.managers.PagesManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;
import java.util.Locale;


/**
 * Class for processing HttpServletRequest "error"
 */
@RequiredArgsConstructor
@Controller
@ControllerAdvice
@RequestMapping("/error")
public class ErrorController {

    private final MessageSource messageManager;

    @LogInvocation
    @GetMapping
    public String error() {
        return PagesManager.PAGE_404;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String applicationError(ServiceException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager.getMessage(e.getMessage(), null, locale));
        return PagesManager.PAGE_404;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String applicationError(LoginUserException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager
                .getMessage("msg.incorrect.email.password", null, locale));
        return PagesManager.PAGE_LOGIN;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String applicationError(FileNotFoundException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager
                .getMessage("msg.internal.error", null, locale));
        return PagesManager.PAGE_ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String applicationError(IllegalArgumentException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager.getMessage(e.getMessage(), null, locale));
        return PagesManager.PAGE_ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String applicationError(RuntimeException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager
                .getMessage("msg.internal.error", null, locale));
        return PagesManager.PAGE_ERROR;
    }
}