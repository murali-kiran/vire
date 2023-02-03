package com.vire.dao;

import com.vire.dto.AdminMessageDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="t_admin_message")
@Data
public class AdminMessageDao {

    @Id
    @Column(name = "t_admin_message_id")
    private Long adminMessageId;
    

    @Column(name = "message_type", nullable = false)
    private String messageType;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "created_time", nullable = false , updatable = false)
    public Long createdTime;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    @PrePersist
    public void onPrePersist() {
        this.setCreatedTime(Instant.now().toEpochMilli());
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    public AdminMessageDto toDto() {

        var dto = new AdminMessageDto();

        dto.setAdminMessageId(this.getAdminMessageId());
        
        dto.setMessageType(this.getMessageType());
        dto.setMessage(this.getMessage());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static AdminMessageDao fromDto(final AdminMessageDto dto) {

        var adminMessage = new AdminMessageDao();

        adminMessage.setAdminMessageId(dto.getAdminMessageId());
        
        adminMessage.setMessageType(dto.getMessageType());
        adminMessage.setMessage(dto.getMessage());

        return adminMessage;
    }
}
