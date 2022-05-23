package com.vire.common;

import com.vire.exception.DuplicateEmailorMobileNumberFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = {DuplicateEmailorMobileNumberFoundException.class})
    @ResponseStatus(value = HttpStatus.FOUND)
    public ErrorMessage resourceNotFoundException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.FOUND.value(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }
}
