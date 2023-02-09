package com.vire.model.response;

import lombok.Data;

@Data
public class AdminPortalLoginResponse {
    private String status;
    private String email;
    private Long expiresIn;
}
