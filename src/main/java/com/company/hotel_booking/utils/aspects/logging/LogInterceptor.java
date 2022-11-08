package com.company.hotel_booking.utils.aspects.logging;

import com.company.hotel_booking.utils.exceptions.ImageUploadingException;
import com.company.hotel_booking.utils.exceptions.LoginException;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 *
 */
@Component
@Aspect
@Log4j2
public class LogInterceptor {

    @Before("@annotation(com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation)")
    public void logMethodCallServlet(JoinPoint jp) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.debug("Calling a method " + methodName + " from " + className);
    }

    @Before("@annotation(com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer)")
    public void logMethodCallService(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        log.debug("Calling a service method " + methodName + " with " + Arrays.toString(args));
    }

    @AfterThrowing(value = "@annotation(com.company.hotel_booking.utils.aspects.logging.annotations.LoginEx)",
            throwing = "e")
    public void afterThrowingLogin(JoinPoint jp, LoginException e) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.error("Class " + className + " method " + methodName + " error. Exception is " + e);
    }

    @AfterThrowing(value = "@annotation(com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx)",
            throwing = "e")
    public void afterThrowingService(JoinPoint jp, ServiceException e) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.error("Class " + className + " method " + methodName + " error. Exception is " + e);
    }

    @AfterThrowing(value = "@annotation(com.company.hotel_booking.utils.aspects.logging.annotations.ImageUploadingEx)",
            throwing = "e")
    public void afterThrowingNotFound(JoinPoint jp, ImageUploadingException e) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.error(
                "Class " + className + " method " + methodName + " error. Exception is " + e);
    }
}