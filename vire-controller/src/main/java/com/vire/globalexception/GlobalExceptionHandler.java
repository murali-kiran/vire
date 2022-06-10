package com.vire.globalexception;

import com.vire.exception.FileStorageException;
import com.vire.exception.LoginException;
import com.vire.exception.VerifyEmailMobileNumberException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleMultipartException(HttpServletRequest request) {
        return new ErrorInfo(request, "Invalid Upload Request");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorInfo handleMethodNotSupported(HttpServletRequest request) {
        return new ErrorInfo(request, "HTTP request method not supported for this operation.");
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorInfo handleRuntimeException(HttpServletRequest request, RuntimeException re) {
        re.printStackTrace();
        return new ErrorInfo(request, re.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo handleIOException(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        return new ErrorInfo(request, "IO Error: IOException ");
    }
    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo handleFileUploadException(HttpServletRequest request, Exception ex){
        ex.printStackTrace();
        return new ErrorInfo(request, "FILE UPLOAD DOWNLOAD Error: " + ex.getMessage());
    }
    @ExceptionHandler(VerifyEmailMobileNumberException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ErrorInfo handleEmailMobileNumberException(HttpServletRequest request, Exception ex){
        ex.printStackTrace();
        return new ErrorInfo(request, "ALREADY EXISTS Error: " + ex.getMessage());
    }
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ErrorInfo handleLoginException(HttpServletRequest request, Exception ex){
        ex.printStackTrace();
        return new ErrorInfo(request, "Error: " + ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleValidationException(HttpServletRequest request, MethodArgumentNotValidException ex){
        ex.printStackTrace();
        return new ErrorInfo(request, "Error: " +ex.getBindingResult().getFieldErrors().stream().map(err -> err.getDefaultMessage())
                .collect(java.util.stream.Collectors.joining(", ")));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo handleException(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        return new ErrorInfo(request, "Error: Something Went Wrong!");
    }
}
