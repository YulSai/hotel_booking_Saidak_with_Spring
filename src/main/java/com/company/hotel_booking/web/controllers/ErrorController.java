package com.company.hotel_booking.web.controllers;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.exceptions.ImageUploadingException;
import com.company.hotel_booking.utils.exceptions.LoginException;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import com.company.hotel_booking.utils.constants.PagesConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * Class for processing HttpServletRequest "error"
 */
@RequiredArgsConstructor
@Controller
@ControllerAdvice
@RequestMapping("/error")
public class ErrorController {

    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping
    public String error() {
        return PagesConstants.PAGE_404;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleServiceException(ServiceException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PagesConstants.PAGE_ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleLoginException(LoginException e, Model model) {
        model.addAttribute("message", messageSource
                .getMessage("msg.incorrect.email.password", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_LOGIN;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleImageUploadingException(ImageUploadingException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PagesConstants.PAGE_ERROR;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFormatException(MethodArgumentTypeMismatchException e, Model model) {
        model.addAttribute("message", messageSource
                .getMessage("msg.incorrect.format.url", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e, Model model) {
        model.addAttribute("message", messageSource
                .getMessage("msg.data.error", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_ERROR;
    }
}