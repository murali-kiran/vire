package com.vire.model.request;

import com.vire.dto.SocialDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialRequest {

    private  String socialId;
    @NotBlank(message = "Profile required")
    @Pattern(regexp="(^[0-9]*$)", message = "Chat Initiator profile id must be numeric")
    private  String profileId;
    @NotBlank(message = "Category required")
    private  String categoryId;
    @Pattern(regexp="(^[0-9]*$)", message = "Chat Initiator profile id must be numeric")
    @NotBlank(message = "Social post type required")
    private  String type;
    @NotBlank(message = "Subject required")
    private  String subject;
    @NotBlank(message = "Description required")
    private  String description;
    @NotBlank(message = "File required")
    @Pattern(regexp="(^[0-9]*$)", message = "Chat Initiator profile id must be numeric")
    private  String fileId;
    @Pattern(regexp="(^$|[0-9]{10})", message = "Contact must be numeric and 10 digits")
    private  String contact;
    @Pattern(regexp="(^$|[0-9]{10})", message = "Alternate contact must be numeric and 10 digits")
    private  String alternateContact;
    private  Long createdTime;
    private  Long updatedTime;
    private List<SocialSendToRequest> sendTo;

    public SocialDto toDto(){
        var dto = new SocialDto();
        dto.setSocialId(this.getSocialId() == null ? null : Long.valueOf(this.getSocialId()));
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));
        dto.setCategoryId(this.getCategoryId() == null ? null : Long.valueOf(this.getCategoryId()));
        dto.setType(this.getType());
        dto.setSubject(this.getSubject());
        dto.setDescription(this.getDescription());
        dto.setContact(this.getContact());
        dto.setAlternateContact(this.getAlternateContact());
        dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());
        if (this.getSendTo() != null && !this.getSendTo().isEmpty()) {
            dto.setSendTo(this.getSendTo()
                    .stream()
                    .map(sendTo -> sendTo.toDto())
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }
}
