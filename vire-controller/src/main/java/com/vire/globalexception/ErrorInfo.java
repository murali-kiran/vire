package com.vire.globalexception;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
@Data
public class ErrorInfo {
    private String url;
    private String exception;

    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.exception = ex.getLocalizedMessage();
    }

    public ErrorInfo(HttpServletRequest request, String message) {
        this.url = String.valueOf(request.getRequestURL());
        this.exception = message;
    }
}
