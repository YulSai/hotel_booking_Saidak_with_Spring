package com.company.hotel_booking.web.controller;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.exceptions.LoginUserException;
import com.company.hotel_booking.utils.exceptions.RegistrationException;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import com.company.hotel_booking.utils.managers.MessageManager;
import com.company.hotel_booking.utils.managers.PagesManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;


/**
 * Class for processing HttpServletRequest "error"
 */
@RequiredArgsConstructor
@Controller
@ControllerAdvice
@RequestMapping("/error")
public class ErrorController {

    private final MessageManager messageManager;

    @LogInvocation
    @GetMapping
    public String error() {
        return PagesManager.PAGE_404;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String applicationError(ServiceException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PagesManager.PAGE_404;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String applicationError(LoginUserException e, Model model) {
        model.addAttribute("message", messageManager.getMessage("msg.incorrect.email.password"));
        return PagesManager.PAGE_LOGIN;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String applicationError(RegistrationException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PagesManager.PAGE_CREATE_USER;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String applicationError(FileNotFoundException e, Model model) {
        model.addAttribute("message", messageManager.getMessage("msg.internal.error"));
        return PagesManager.PAGE_ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String applicationError(IllegalArgumentException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PagesManager.PAGE_ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String applicationError(RuntimeException e, Model model) {
        model.addAttribute("message", messageManager.getMessage("msg.internal.error"));
        return PagesManager.PAGE_ERROR;
    }
}