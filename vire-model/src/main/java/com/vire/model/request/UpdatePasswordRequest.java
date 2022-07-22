package com.vire.model.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private Long profileId;
    private String emailOrphonenumber;
    private String oldPassword;
    private String newPassword;
}
