package com.vire.dao;

import com.vire.dto.SocialPostSendToDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_social_post_send_to")
@Data
public class SocialPostSendToDao {
    @Id
    @Column(name = "id", nullable = false)
    private Long sendToId;

    @Column(name = "type", length = 191)
    private String type;

    @Column(name = "value", length = 191)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "social_id")
    private SocialDao social;

    public SocialPostSendToDto toDto(){
        var dto = new SocialPostSendToDto();
        dto.setSendToId(this.getSendToId());
        dto.setType(this.getType());
        dto.setValue(this.getValue());
        return dto;
    }

    public static SocialPostSendToDao fromDto(SocialPostSendToDto dto){
        var dao = new SocialPostSendToDao();
        dao.setSendToId(dto.getSendToId());
        dao.setType(dto.getType());
        dao.setValue(dto.getValue());
        return dao;
    }
}