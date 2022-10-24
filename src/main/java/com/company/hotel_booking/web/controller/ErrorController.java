package com.company.hotel_booking.web.controller;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.exceptions.ConnectionPoolException;
import com.company.hotel_booking.utils.exceptions.LoginUserException;
import com.company.hotel_booking.utils.exceptions.NotFoundException;
import com.company.hotel_booking.utils.exceptions.RegistrationException;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import com.company.hotel_booking.utils.managers.MessageManager;
import com.company.hotel_booking.utils.managers.PagesManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@RequiredArgsConstructor
@Controller
@ControllerAdvice
@RequestMapping("/error")
public class ErrorController {

    private final MessageManager messageManager;
    @LogInvocation
    @GetMapping
    public String error() {
        log.error("Incorrect address");
        return PagesManager.PAGE_ERROR;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String applicationError(RuntimeException e, Model model) {
        model.addAttribute("message", messageManager.getMessage("msg.internal.error"));
        return PagesManager.PAGE_ERROR;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String applicationError(FileNotFoundException e, Model model) {
        model.addAttribute("message",e.getMessage());
        return PagesManager.PAGE_ERROR;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String applicationError(ServiceException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PagesManager.PAGE_ERROR;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String applicationError(LoginUserException e, Model model) {
        model.addAttribute("message", messageManager.getMessage("msg.incorrect.email.password"));
        return PagesManager.PAGE_LOGIN;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String applicationError(RegistrationException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PagesManager.PAGE_CREATE_USER;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String applicationError(NotFoundException e, Model model) {
        model.addAttribute("message", messageManager.getMessage("msg.not.found"));
        return PagesManager.PAGE_404;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String applicationError(ConnectionPoolException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PagesManager.PAGE_404;
    }
}