package com.vire.dao;

import javax.persistence.*;

import com.vire.dto.SocialChatDto;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "t_social_post_chat")
@Data
public class SocialChatDao extends BaseDao{
    @Id
    @Column(name = "social_post_chat_id", nullable = false)
    private Long id;

    @Column(name = "chat_initiator_profile_id", nullable = false)
    private Long chatInitiatorProfileId;

    @Column(name = "sender_profile_id", nullable = false)
    private Long senderProfileId;

    @Column(name = "message", length = 191)
    private String message;

    @Column(name = "social_id", nullable = false)
    private Long socialPostId;

    @Column(name = "chat_time", nullable = false)
    private Long chatTime;
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
    public SocialChatDto toDto(){
        var dto = new SocialChatDto();
        dto.setId(this.getId());
        dto.setChatInitiatorProfileId(this.getChatInitiatorProfileId());
        dto.setSenderProfileId(this.getSenderProfileId());
        dto.setMessage(this.getMessage());
        dto.setChatTime(this.getChatTime());
        dto.setSocialPostId(this.getSocialPostId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());
        return dto;
    }
    public static SocialChatDao fromDto(SocialChatDto dto){
        var dao = new SocialChatDao();
        dao.setId(dto.getId());
        dao.setChatInitiatorProfileId(dto.getChatInitiatorProfileId());
        dao.setSenderProfileId(dto.getSenderProfileId());
        dao.setMessage(dto.getMessage());
        dao.setChatTime(dto.getChatTime());
        dao.setSocialPostId(dto.getSocialPostId());
        return dao;
    }

}