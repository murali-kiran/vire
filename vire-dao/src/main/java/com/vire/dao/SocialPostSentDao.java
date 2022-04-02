package com.vire.dao;

import com.vire.dto.SocialPostLikeDto;
import com.vire.dto.SocialPostSentDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_social_post_sent")
@Data
public class SocialPostSentDao {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", length = 191)
    private String type;

    @Column(name = "value", length = 191)
    private String value;

    public SocialPostSentDto toDto(){
        var dto = new SocialPostSentDto();
        dto.setId(this.getId());
        dto.setType(this.getType());
        dto.setValue(this.getValue());
        return dto;
    }

    public static SocialPostSentDao fromDto(SocialPostSentDto dto){
        var dao = new SocialPostSentDao();
        dao.setId(dto.getId());
        dao.setType(dto.getType());
        dao.setValue(dto.getValue());
        return dao;
    }
}