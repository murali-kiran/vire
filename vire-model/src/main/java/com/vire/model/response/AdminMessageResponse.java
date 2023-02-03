package com.vire.model.response;

import com.vire.dto.AdminMessageDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AdminMessageResponse {

    private String adminMessageId;
    
    private String messageType;
    private String message;
    private Long createdTime;
    private Long updatedTime;

    public static AdminMessageResponse fromDto(final AdminMessageDto dto) {

        var adminMessage = new AdminMessageResponse();

        adminMessage.setAdminMessageId(String.valueOf(dto.getAdminMessageId()));
        adminMessage.setMessageType(dto.getMessageType());
        adminMessage.setMessage(dto.getMessage());
        adminMessage.setCreatedTime(dto.getCreatedTime());
        adminMessage.setUpdatedTime(dto.getUpdatedTime());

        return adminMessage;
    }
}
