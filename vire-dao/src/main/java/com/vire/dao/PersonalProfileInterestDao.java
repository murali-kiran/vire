package com.vire.dao;

import com.vire.dto.PersonalProfileInterestDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name="personal_profile_interest")
@Data
public class PersonalProfileInterestDao {

    @Id
    @GeneratedValue
    @Column(name = "personal_profile_interest_id")
    private Long id;

    @Column(name = "interest", nullable = false)
    private String interest;

    @ManyToOne(fetch = FetchType.LAZY)
    private PersonalProfileDao personalProfile;

    public PersonalProfileInterestDto toDto() {
        return new ModelMapper().map(this,PersonalProfileInterestDto.class);
    }

    public static PersonalProfileInterestDao fromDto(final PersonalProfileInterestDto dto) {
        return new ModelMapper().map(dto, PersonalProfileInterestDao.class);
    }

}
