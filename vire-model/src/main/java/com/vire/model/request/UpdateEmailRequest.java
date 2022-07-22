package com.vire.model.request;

import lombok.Data;

@Data
public class UpdateEmailRequest {
    private Long profileId;
    private String emailOrphonenumber;
    private String newEmail;
}
