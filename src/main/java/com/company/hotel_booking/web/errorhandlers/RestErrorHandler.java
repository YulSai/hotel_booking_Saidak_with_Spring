package com.company.hotel_booking.web.errorhandlers;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.utils.exceptions.ForbiddenException;
import com.company.hotel_booking.utils.exceptions.NotFoundException;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import com.company.hotel_booking.utils.exceptions.rest.MethodNotAllowedException;
import com.company.hotel_booking.utils.exceptions.rest.ValidationException;
import com.company.hotel_booking.utils.exceptions.rest.dto.ErrorDto;
import com.company.hotel_booking.utils.exceptions.rest.dto.ValidationResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class for processing errors from REST
 */
@RequiredArgsConstructor
@Controller
@RestControllerAdvice("com.company.hotel_booking.web.controllers.rest")
public class RestErrorHandler {
    private final MessageSource messageSource;

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto error(ServiceException e) {
        return new ErrorDto(e.getMessage());
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto error(NotFoundException e) {
        return new ErrorDto(e.getMessage());
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorDto error(MethodNotAllowedException e) {
        return new ErrorDto(e.getMessage());
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto error(ForbiddenException e) {
        return new ErrorDto(messageSource
                .getMessage("msg.insufficient.rights", null,  LocaleContextHolder.getLocale()));
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto error(NumberFormatException e) {
        return new ErrorDto(messageSource
                .getMessage("msg.incorrect.format.url", null,  LocaleContextHolder.getLocale()));
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto error(MethodArgumentTypeMismatchException e) {
        return new ErrorDto(messageSource
                .getMessage("msg.incorrect.format.url", null, LocaleContextHolder.getLocale()));
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto error(Exception e) {
        return new ErrorDto(messageSource
                .getMessage("msg.internal.error", null,  LocaleContextHolder.getLocale()));
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationResultDto validationError(ValidationException e) {
        Map<String, List<String>> errors = mapErrors(e.getErrors());
        return new ValidationResultDto(errors);
    }

    private Map<String, List<String>> mapErrors(Errors rawErrors) {
        return rawErrors.getFieldErrors()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                FieldError::getField,
                                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                        )
                );
    }
}
