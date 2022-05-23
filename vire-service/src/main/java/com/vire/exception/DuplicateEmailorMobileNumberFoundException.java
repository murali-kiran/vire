package com.vire.exception;

public class DuplicateEmailorMobileNumberFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicateEmailorMobileNumberFoundException(String msg) {
        super(msg);
    }
}
