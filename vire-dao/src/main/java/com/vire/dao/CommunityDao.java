package com.vire.dao;

import com.vire.dto.CommunityDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="community")
@Data
public class CommunityDao {

    @Id
    @Column(name = "community_id")
    private Long communityId;
    

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "creator_profile_id", nullable = false)
    private Long creatorProfileId;

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name = "rules", nullable = false)
    private String rules;

    @Column(name = "member_proof_required", nullable = false)
    private Boolean memberProofRequired;

    /*@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityProfileDao> profiles;*/

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

    public CommunityDto toDto() {

        var dto = new CommunityDto();

        dto.setCommunityId(this.getCommunityId());
        
        dto.setName(this.getName());
        dto.setDescription(this.getDescription());
        dto.setCreatorProfileId(this.getCreatorProfileId());
        dto.setFileId(this.getFileId());
        dto.setRules(this.getRules());
        dto.setMemberProofRequired(this.getMemberProofRequired());
        /*if (this.getProfiles() != null && !this.getProfiles().isEmpty()) {
            dto.setProfiles(this.getProfiles()
                    .stream()
                    .map(child -> child.toDto())
                    .collect(Collectors.toList()));
        }*/


        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static CommunityDao fromDto(final CommunityDto dto) {

        var community = new CommunityDao();

        community.setCommunityId(dto.getCommunityId());
        
        community.setName(dto.getName());
        community.setDescription(dto.getDescription());
        community.setCreatorProfileId(dto.getCreatorProfileId());
        community.setFileId(dto.getFileId());
        community.setRules(dto.getRules());
        community.setMemberProofRequired(dto.getMemberProofRequired());
        /*if (dto.getProfiles() != null && !dto.getProfiles().isEmpty()) {
            community.setProfiles(dto.getProfiles()
                    .stream()
                    .map(child -> CommunityProfileDao.fromDto(child))
                    .collect(Collectors.toList()));
        }*/


        return community;
    }
}
