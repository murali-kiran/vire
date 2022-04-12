package com.vire.dao;

import com.vire.dto.PersonalProfileDto;
import com.vire.enumeration.BloodDonateWillingness;
import com.vire.enumeration.WorkStatus;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "personal_profile")
@Data
public class PersonalProfileDao {

    @Id
    @Column(name = "profile_id")
    private Long personalProfileId;

    @Column(name = "school_board", nullable = true)
    private String schoolBoard;

    @Column(name = "school_name", nullable = true)
    private String schoolName;

    @Column(name = "intermediate_board", nullable = true)
    private String intermediateBoard;

    @Column(name = "intermediate_college_name", nullable = true)
    private String intermediateCollegeName;

    @Column(name = "graduation_board", nullable = true)
    private String graduationBoard;

    @Column(name = "graduation_college_name", nullable = true)
    private String graduationCollegeName;

    @Column(name = "postGraduation_board", nullable = true)
    private String postGraduationBoard;

    @Column(name = "postGraduation_college_name", nullable = true)
    private String postGraduationCollegeName;

    @Column(name = "work_status", nullable = true)
    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;//	Enum

    @Column(name = "field_profession_business", nullable = true)
    private String fieldProfessionBusiness;

    @Column(name = "designation", nullable = true)
    private String designation;

    @Column(name = "organization_name", nullable = true)
    private String organizationName;

    @Column(name = "organization_location", nullable = true)
    private String organizationLocation;

    @Column(name = "blood_group", nullable = true)
    private String bloodGroup;

    @Column(name = "blood_donate_willingness", nullable = false)
    @Enumerated(EnumType.STRING)
    private BloodDonateWillingness bloodDonateWillingness;//	Enum

    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name = "present_address_id")
    private AddressDao presentAddress;

    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name = "permanent_address_id")
    private AddressDao permanentAddress;

    @OneToMany(mappedBy = "personalProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "profile_id")
    private List<PersonalProfileInterestDao> interests;

    @OneToOne
    @MapsId
    @JoinColumn(name = "profile_id")
    private ProfileDao profile;

    public PersonalProfileDto toDto() {
        return new ModelMapper().map(this,PersonalProfileDto.class);
    }

    public static PersonalProfileDao fromDto(final PersonalProfileDto dto) {
        return new ModelMapper().map(dto, PersonalProfileDao.class);
    }
}
