package com.vire.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePasswordResponse {
    boolean status;
    String message;
}
