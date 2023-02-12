package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.AdminMessageDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AdminMessageRequest {

    private String adminMessageId;
    private String sendTo;
    private String messageType;
    private String message;

    public AdminMessageDto toDto(Snowflake snowflake) {

        var dto = new AdminMessageDto();

        if(snowflake == null) {
            dto.setAdminMessageId(this.getAdminMessageId() == null ? null : Long.valueOf(this.getAdminMessageId()));
        }else {
            dto.setAdminMessageId(this.getAdminMessageId() == null ? snowflake.nextId() : Long.valueOf(this.getAdminMessageId()));
        }
        
        dto.setMessageType(this.getMessageType());
        dto.setMessage(this.getMessage());
        dto.setSendTo(Long.valueOf(this.getSendTo()));

        return dto;
    }

    public AdminMessageDto toDto() {
        return toDto(null);
    }
}
