package com.vire.dao;

import com.vire.dto.ProfileDto;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profiles")
@Data
public class ProfileDao {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    public ProfileDto toDto() {
        var dto = new ProfileDto();

        dto.setId(this.getId());
        dto.setUserId(this.getUserId());
        dto.setName(this.getName());

        return dto;
    }

    public static ProfileDao fromDto(final ProfileDto dto) {
        var dao = new ProfileDao();

        dao.setId(dto.getId());
        dao.setUserId(dto.getUserId());
        dao.setName(dto.getName());

        return dao;
    }
}
