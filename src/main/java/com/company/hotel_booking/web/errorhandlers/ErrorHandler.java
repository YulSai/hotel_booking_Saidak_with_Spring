package com.company.hotel_booking.web.errorhandlers;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.constants.PagesConstants;
import com.company.hotel_booking.utils.exceptions.NotFoundException;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import com.company.hotel_booking.utils.exceptions.users.ImageUploadingException;
import com.company.hotel_booking.utils.exceptions.users.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * Class for processing errors from View
 */
@RequiredArgsConstructor
@Controller
@ControllerAdvice("com.company.hotel_booking.web.controllers.view")
@RequestMapping("/error")
public class ErrorHandler {

    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping
    public String error(Model model) {
        model.addAttribute("message", messageSource
                .getMessage("msg.main.no.such.page", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_ERROR;
    }

    @LogInvocation
    @GetMapping("/errorAccessDenied")
    public String errorAccessDenied() {
        return PagesConstants.PAGE_ERROR_ACCESS;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleServiceException(ServiceException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PagesConstants.PAGE_ERROR_HANDLER;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PagesConstants.PAGE_ERROR_HANDLER;
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
        return PagesConstants.PAGE_ERROR_HANDLER;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, Model model) {
        model.addAttribute("message", messageSource
                .getMessage("msg.incorrect.format.url", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_ERROR_HANDLER;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNumberFormatException(NumberFormatException e, Model model) {
        model.addAttribute("message", messageSource
                .getMessage("msg.incorrect.format.url", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_ERROR_HANDLER;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e, Model model) {
        model.addAttribute("message", messageSource
                .getMessage("msg.data.error", null, LocaleContextHolder.getLocale()));
        return PagesConstants.PAGE_ERROR_HANDLER;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException e, Model model) {
        return PagesConstants.PAGE_ERROR_ACCESS;
    }
}