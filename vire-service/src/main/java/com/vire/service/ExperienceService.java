package com.vire.service;

import com.vire.dto.ExperienceDto;
import com.vire.dto.ExperienceViewsCountDto;
import com.vire.model.request.ExperienceRequest;
import com.vire.model.response.*;
import com.vire.repository.ExperienceRepository;
import com.vire.repository.ExperienceViewsCountRepository;
import com.vire.utils.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExperienceService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ExperienceRepository experienceRepository;
  @Autowired
  ProfileService profileService;
  @Autowired
  MasterService masterService;
  @Autowired
  ExperienceCommentService experienceCommentService;
  @Autowired
  ExperienceCommentReplyService experienceCommentReplyService;
  @Autowired
  ExperienceLikesService experienceLikesService;
  @Autowired
  ExperienceViewsCountRepository experienceViewsCountRepository;


  public ExperienceResponse create(final ExperienceRequest request) {

    var dto = request.toDto(snowflake);

    return ExperienceResponse.fromDto(experienceRepository.create(dto));
  }

  public ExperienceResponse update(final ExperienceRequest request) {

    var dto = request.toDto();

    return ExperienceResponse.fromDto(experienceRepository.update(dto));
  }

  public ExperienceResponse delete(final Long experienceId) {

    return experienceRepository.delete(experienceId)
            .map(dto -> ExperienceResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceResponse> getAll() {

    return experienceRepository
            .getAll()
            .stream()
            .map(dto -> ExperienceResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ExperienceResponse retrieveById(Long experienceId) {

    return experienceRepository
            .retrieveById(experienceId)
            .map(dto -> ExperienceResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceResponse> search(final String searchString) {

    return experienceRepository
            .search(searchString)
            .stream()
            .map(dto -> ExperienceResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

    public List<ExperienceDetailResponse> retrieveAllByProfile(Long profileId) {
      List<String> experienceIds = getAll().stream().map(ExperienceResponse::getExperienceId).collect(Collectors.toList());
      List<ExperienceDetailResponse> experienceDetailResponseList = new ArrayList<>();
      for (String experienceId : experienceIds) {
        var experienceDetailResponse = setExperienceDetails(Long.valueOf(experienceId), false, profileId);
        /*List<String> profileIds = experienceDetailResponse.getLikesResponseList() != null ? experienceDetailResponse.getLikesResponseList().stream().map(ExperienceLikesResponse::getLikerProfileId).collect(Collectors.toList()) : null;
        experienceDetailResponse.setLoginUserLiked((profileIds != null && profileIds.contains(profileId+"")) ? true : false);
        */
        experienceDetailResponse.setMinimalProfileResponse(profileService.retrieveProfileDtoById(Long.valueOf(experienceDetailResponse.getProfileId())));
        experienceDetailResponseList.add(experienceDetailResponse);
      }
      return experienceDetailResponseList;
    }

  public ExperienceDetailResponse retrieveExperienceDetailsById(Long experienceId, Long profileId) {
    log.info("Experience ID###:"+experienceId);
    ExperienceDetailResponse experienceDetailResponse = setExperienceDetails(experienceId, true, profileId);
    return experienceDetailResponse;
  }

  private ExperienceDetailResponse setExperienceDetails(Long experienceId, Boolean setViewCount, Long profileId){
    ExperienceDetailResponse experienceDetailResponse = experienceRepository.retrieveById(experienceId)
            .map(dto -> ExperienceDetailResponse.fromDto(dto))
            .get();
    if(experienceDetailResponse == null){
      throw new RuntimeException("Record not found for experience id:"+experienceId);
    }
    var experienceDto = experienceViewsCountRepository.retrieveByProfileIdExperienceId(experienceId, profileId);
    List<ExperienceCommentResponse> experienceCommentsList = experienceCommentService.search("experienceId:" + experienceDetailResponse.getExperienceId());
    List<ExperienceLikesResponse> likesList = experienceLikesService.search("experienceId:" + experienceDetailResponse.getExperienceId());
    if(setViewCount) {
      if(experienceDto.isEmpty()) {
        ExperienceViewsCountDto experienceViewsCountDto = new ExperienceViewsCountDto();
        experienceViewsCountDto.setExperienceId(experienceId);
        experienceViewsCountDto.setProfileId(profileId);
        experienceViewsCountDto.setViewsCountId(snowflake.nextId());
        experienceViewsCountRepository.create(experienceViewsCountDto);
      }
      experienceDetailResponse.setCommentResponseList(experienceCommentsList);
      experienceDetailResponse.setLikesResponseList(likesList);
    }
    List<String> profileIds = likesList == null ? null : likesList.stream().map(ExperienceLikesResponse::getLikerProfileId).collect(Collectors.toList());
    experienceDetailResponse.setLoginUserLiked((profileIds != null && profileIds.contains(profileId+"")) ? true : false);
    experienceDetailResponse.setLikesCount( likesList == null ? 0 : likesList.size() );
    Long viewsCount = experienceViewsCountRepository.countByExperienceId(experienceId);
    MasterResponse experienceCategoryResponse = masterService.retrieveById(Long.valueOf(experienceDetailResponse.getCategoryId()));
    experienceDetailResponse.setCategoryResponse(experienceCategoryResponse);
    List<ExperienceCommentReplyResponse> replyList = experienceCommentReplyService.search("experienceId:" + experienceDetailResponse.getExperienceId());
    DateFormat sdf2 = new SimpleDateFormat("MMMM dd 'at' HH:mm");
    sdf2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
    experienceDetailResponse.setCreatedTimeStr(sdf2.format(new Date(experienceDetailResponse.getCreatedTime())));
    MinimalProfileResponse minimalProfileResponse = new MinimalProfileResponse();
    minimalProfileResponse.setProfileId(experienceDetailResponse.getProfileId());
    experienceDetailResponse.setMinimalProfileResponse(minimalProfileResponse);
    experienceDetailResponse.getMinimalProfileResponse().cloneProperties(
            profileService.retrieveProfileDtoById(
                    Long.valueOf(experienceDetailResponse.getProfileId())));
    experienceDetailResponse.setCommentsCount(( experienceCommentsList != null ? experienceCommentsList.size() : 0 ) + (replyList != null ? replyList.size() : 0));
    experienceDetailResponse.setViewsCount(viewsCount);
    return experienceDetailResponse;
  }


}
