package com.vire.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailOrphonenumber;
    private String password;
}
