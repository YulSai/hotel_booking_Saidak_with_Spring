package com.company.hotel_booking.aspects.logging;

import com.company.hotel_booking.exceptions.DaoException;
import com.company.hotel_booking.exceptions.LoginUserException;
import com.company.hotel_booking.exceptions.RegistrationException;
import com.company.hotel_booking.exceptions.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Log4j2
public class LogInterceptor {

    @Before("@annotation(LogInvocationServer)")
    public void logMethodCallService(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        log.debug("Calling a service method " + methodName + " with " + Arrays.toString(args));
    }

    @Before("@annotation(LogInvocationRepository)")
    public void logMethodCallRepository(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        log.debug("Accessing the database using the method " + methodName + " with " + Arrays.toString(args));
    }

    @AfterThrowing(value = "@annotation(LoginEx)", throwing = "e")
    public void afterThrowingLogin(JoinPoint jp, LoginUserException e) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.error("Class " + className + " method " + methodName + " error. Exception is " + e);
    }

    @AfterThrowing(value = "@annotation(RegistrationEx)", throwing = "e")
    public void afterThrowingRegistration(JoinPoint jp, RegistrationException e) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.error("Class " + className + " method " + methodName + " error. Exception is " + e);
    }

    @AfterThrowing(value = "@annotation(ServiceEx)", throwing = "e")
    public void afterThrowingService(JoinPoint jp, ServiceException e) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.error("Class " + className + " method " + methodName + " error. Exception is " + e);
    }

    @AfterThrowing(value = "@annotation(DaoEx)", throwing = "e")
    public void afterThrowingDao(JoinPoint jp, DaoException e) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.error("Class " + className + " method " + methodName + " error. Exception is " + e);
    }


}