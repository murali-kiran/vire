package com.vire.dao;

import com.vire.dto.SocialSendToDto;
import com.vire.enumeration.SendToEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "t_social_post_send_to")
@Data
public class SocialSendToDao extends BaseDao{
    @Id
    @Column(name = "social_post_send_to_id", nullable = false)
    private Long sendToId;


    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "value", length = 191)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "social_id")
    private SocialDao social;

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
    @Override
    public String toString() {
        return "SocialPostSendToDao{" +
                "sendToId=" + sendToId +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", social=" + social.getSocialId() +
                '}';
    }

    public SocialSendToDto toDto(){
        var dto = new SocialSendToDto();
        dto.setSendToId(this.getSendToId());
        dto.setType(this.getType());
        dto.setValue(this.getValue());
        dto.setSocialId(this.getSocial().getSocialId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());
        return dto;
    }

    public static SocialSendToDao fromDto(SocialSendToDto dto){
        var dao = new SocialSendToDao();
        dao.setSendToId(dto.getSendToId());
        dao.setType(dto.getType());
        dao.setValue(dto.getValue());
        return dao;
    }
}