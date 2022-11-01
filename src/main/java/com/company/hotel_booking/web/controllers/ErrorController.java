package com.company.hotel_booking.web.controllers;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.exceptions.ImageUploadingException;
import com.company.hotel_booking.utils.exceptions.LoginException;
import com.company.hotel_booking.utils.exceptions.NotFoundException;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
    public String handleServiceException(ServiceException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager.getMessage(e.getMessage(), null, locale));
        return PagesManager.PAGE_ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleLoginException(LoginException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager
                .getMessage("msg.incorrect.email.password", null, locale));
        return PagesManager.PAGE_LOGIN;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleImageUploadingException(ImageUploadingException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager
                .getMessage(e.getMessage(), null, locale) + e.getCause().getMessage());
        return PagesManager.PAGE_ERROR;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFormatException(MethodArgumentTypeMismatchException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager
                .getMessage("msg.incorrect.format.url", null, locale));
        return PagesManager.PAGE_ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager
                .getMessage(e.getClass().getSimpleName() + "msg.data.error", null, locale));
        return PagesManager.PAGE_ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String applicationError(NotFoundException e, Model model, Locale locale) {
        model.addAttribute("message", messageManager
                .getMessage(e.getMessage(), null, locale));
        return PagesManager.PAGE_ERROR;
    }
}