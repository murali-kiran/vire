package com.vire.dao;

import com.vire.dto.PersonalProfileInterestDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "personal_profile_interest")
@Data
public class PersonalProfileInterestDao {

    @Id
    @Column(name = "personal_profile_interest_id")
    private Long interestId;

    @Column(name = "interest", nullable = false)
    private String interest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_profile_id")
    private PersonalProfileDao personalProfile;

    @Column(name = "created_time", nullable = false, updatable = false)
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

    public PersonalProfileInterestDto toDto() {
        return new ModelMapper().map(this, PersonalProfileInterestDto.class);
    }

    public static PersonalProfileInterestDao fromDto(final PersonalProfileInterestDto dto) {
        return new ModelMapper().map(dto, PersonalProfileInterestDao.class);
    }

}
