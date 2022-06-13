package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ChannelDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChannelRequest {

    private String channelId;
    
    private String name;
    private String description;
    private String creatorProfileId;
    private String fileId;
    private String coverFileId;
    //private String rules;
    //private List<ChannelProfileRequest> profiles;

    public ChannelDto toDto(Snowflake snowflake) {

        var dto = new ChannelDto();

        if(snowflake == null) {
            dto.setChannelId(this.getChannelId() == null ? null : Long.valueOf(this.getChannelId()));
        }else {
            dto.setChannelId(snowflake.nextId());
        }
        
        dto.setName(this.getName());
        dto.setDescription(this.getDescription());
        dto.setCreatorProfileId(this.getCreatorProfileId() == null ? null : Long.valueOf(this.getCreatorProfileId()));
        dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));
        dto.setCoverFileId(this.getCoverFileId() == null ? null : Long.valueOf(this.getCoverFileId()));
        //dto.setRules(this.getRules());

        /*if (this.getProfiles() != null && !this.getProfiles().isEmpty()) {
            dto.setProfiles(this.getProfiles()
                    .stream()
                    .map(child -> child.toDto(snowflake))
                    .collect(Collectors.toList()));
        }*/


        return dto;
    }

    public ChannelDto toDto() {
        return toDto(null);
    }
}