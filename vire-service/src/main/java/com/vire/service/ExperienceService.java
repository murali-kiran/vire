package com.vire.service;

import com.vire.model.request.ExperienceRequest;
import com.vire.model.response.*;
import com.vire.repository.ExperienceRepository;
import com.vire.utils.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        var experienceDetailResponse = retrieveExperienceDetailsById(Long.valueOf(experienceId));
        experienceDetailResponse.setMinimalProfileResponse(profileService.retrieveProfileDtoById(Long.valueOf(experienceDetailResponse.getProfileId())));
        experienceDetailResponseList.add(experienceDetailResponse);
      }
      return experienceDetailResponseList;
    }

  /*public ExperienceDetailResponse retrieveExperienceDetailsById(Long experienceId, Long profileId) {
    log.info("Experience ID###:"+experienceId+"$$$Profile ID ID###:"+profileId);
    ExperienceDetailResponse experienceDetailResponse = experienceRepository.retrieveById(experienceId)
            .map(dto -> ExperienceDetailResponse.fromDto(dto))
            .get();
     return setExperienceDetails(experienceDetailResponse);
  }*/

  public ExperienceDetailResponse retrieveExperienceDetailsById(Long experienceId) {
    log.info("Experience ID###:"+experienceId);
    ExperienceDetailResponse experienceDetailResponse = experienceRepository.retrieveById(experienceId)
            .map(dto -> ExperienceDetailResponse.fromDto(dto))
            .get();

    return setExperienceDetails(experienceDetailResponse);
  }

  private ExperienceDetailResponse setExperienceDetails(ExperienceDetailResponse experienceDetailResponse){
    MasterResponse experienceCategoryResponse = masterService.retrieveById(Long.valueOf(experienceDetailResponse.getCategoryId()));
    experienceDetailResponse.setCategoryResponse(experienceCategoryResponse);
    List<ExperienceCommentResponse> experienceCommentsList = experienceCommentService.search("experienceId:" + experienceDetailResponse.getExperienceId());
    List<ExperienceCommentReplyResponse> replyList = experienceCommentReplyService.search("experienceId:" + experienceDetailResponse.getExperienceId());
    List<ExperienceLikesResponse> likesList = experienceLikesService.search("experienceId:" + experienceDetailResponse.getExperienceId());
    experienceDetailResponse.setCommentResponseList(experienceCommentsList);
    experienceDetailResponse.setLikesResponseList(likesList);
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

    return experienceDetailResponse;
  }
}
