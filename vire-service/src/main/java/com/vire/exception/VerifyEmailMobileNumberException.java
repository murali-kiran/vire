package com.vire.exception;

public class VerifyEmailMobileNumberException extends RuntimeException {
    public VerifyEmailMobileNumberException(String message) {
        super(message);
    }

    public VerifyEmailMobileNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}