package com.vire.dao;

import javax.persistence.*;

import com.vire.dto.SocialPostChatDto;
import lombok.Data;

@Entity
@Table(name = "t_social_post_chat")
@Data
public class SocialPostChatDao {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_initiator_profile_id", nullable = false)
    private Long chatInitiatorProfileId;

    @Column(name = "sender_profile_id", nullable = false)
    private Long senderProfileId;

    @Column(name = "message", length = 191)
    private String message;

    @Column(name = "social_post_id", nullable = false)
    private Long socialPostId;

    @Column(name = "chat_time", nullable = false)
    private Long chatTime;

    public SocialPostChatDto toDto(){
        var dto = new SocialPostChatDto();
        dto.setId(this.getId());
        dto.setChatInitiatorProfileId(this.getChatInitiatorProfileId());
        dto.setSenderProfileId(this.getSenderProfileId());
        dto.setMessage(this.getMessage());
        dto.setChatTime(this.getChatTime());
        dto.setSocialPostId(this.getSocialPostId());
        return dto;
    }
    public static SocialPostChatDao fromDto(SocialPostChatDto dto){
        var dao = new SocialPostChatDao();
        dao.setId(dto.getId());
        dao.setChatInitiatorProfileId(dto.getChatInitiatorProfileId());
        dao.setSenderProfileId(dto.getSenderProfileId());
        dao.setMessage(dto.getMessage());
        dao.setChatTime(dto.getChatTime());
        dao.setSocialPostId(dto.getSocialPostId());
        return dao;
    }

}