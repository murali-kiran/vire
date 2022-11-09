package com.vire.dao.view;

import com.vire.dao.CommunityFileDao;
import com.vire.dto.CommunityDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="community")
@Data
public class CommunityViewDao {

    @Id
    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "creator_profile_id", nullable = false)
    private Long creatorProfileId;

    @Column(name = "is_blocked", columnDefinition="BOOLEAN DEFAULT false")
    private Boolean isBlocked = false;


    @Column(name = "created_time", nullable = false , updatable = false)
    public Long createdTime;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;


    public CommunityDto toDto() {

        var dto = new CommunityDto();

        dto.setCommunityId(this.getCommunityId());
        dto.setName(this.getName());
        dto.setDescription(this.getDescription());
        dto.setCreatorProfileId(this.getCreatorProfileId());
        dto.setIsBlocked(this.getIsBlocked());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }


}
