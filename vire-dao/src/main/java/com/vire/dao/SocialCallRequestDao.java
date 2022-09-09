package com.vire.dao;

import com.vire.dto.SocialCallRequestDto;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="social_call_request")
@Data
public class SocialCallRequestDao {

    @Id
    @Column(name = "socail_call_request_id")
    private Long socialCallRequestId;
    

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "requester_profile_id", nullable = false)
    private Long requesterProfileId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "social_id", nullable = false)
    private Long socialId;

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

    public SocialCallRequestDto toDto() {

        var dto = new SocialCallRequestDto();

        dto.setSocialCallRequestId(this.getSocialCallRequestId());
        dto.setSocialId(this.getSocialId());
        dto.setProfileId(this.getProfileId());
        dto.setRequesterProfileId(this.getRequesterProfileId());
        dto.setStatus(this.getStatus());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static SocialCallRequestDao fromDto(final SocialCallRequestDto dto) {

        var socialCallRequest = new SocialCallRequestDao();

        socialCallRequest.setSocialCallRequestId(dto.getSocialCallRequestId());
        
        socialCallRequest.setProfileId(dto.getProfileId());
        socialCallRequest.setRequesterProfileId(dto.getRequesterProfileId());
        socialCallRequest.setStatus(dto.getStatus());
        socialCallRequest.setSocialId(dto.getSocialId());

        return socialCallRequest;
    }
}
