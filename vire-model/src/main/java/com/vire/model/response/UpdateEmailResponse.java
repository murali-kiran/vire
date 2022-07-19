package com.vire.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateEmailResponse {
    boolean status;
    String message;
}
