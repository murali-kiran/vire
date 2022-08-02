package com.vire.model.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String profileId;
    private String emailOrphonenumber;
    private String oldPassword;
    private String newPassword;
}
