package com.vire.service;

import com.vire.model.request.ExperienceLikesRequest;
import com.vire.model.response.ExperienceCommentResponse;
import com.vire.model.response.ExperienceLikesResponse;
import com.vire.model.response.ExperienceResponse;
import com.vire.repository.ExperienceLikesRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceLikesService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ExperienceLikesRepository experienceLikesRepository;

  @Autowired
  ProfileService profileService;

  public ExperienceLikesResponse create(final ExperienceLikesRequest request) {

    var dto = request.toDto(snowflake);

    return ExperienceLikesResponse.fromDto(experienceLikesRepository.create(dto));
  }

  public ExperienceLikesResponse update(final ExperienceLikesRequest request) {

    var dto = request.toDto();

    return ExperienceLikesResponse.fromDto(experienceLikesRepository.update(dto));
  }

  public ExperienceLikesResponse delete(final Long experienceLikesId) {

    return experienceLikesRepository.delete(experienceLikesId)
            .map(dto -> ExperienceLikesResponse.fromDto(dto))
            .get();
  }
  public ExperienceLikesResponse deleteByProfileAndExperience(final Long experienceId, final Long profileId) {

    List<ExperienceLikesResponse> experienceLikesResponses = search("experienceId:" + experienceId + " AND likerProfileId:"+profileId);
    if(experienceLikesResponses != null && !experienceLikesResponses.isEmpty())
    return experienceLikesRepository.delete(Long.valueOf(experienceLikesResponses.get(0).getExperienceLikesId()))
            .map(dto -> ExperienceLikesResponse.fromDto(dto))
            .get();
    else
      throw new RuntimeException("No record found with given experienceId:"+experienceId+" profileId:"+profileId);
  }
  public List<ExperienceLikesResponse> getAll() {

    return experienceLikesRepository
            .getAll()
            .stream()
            .map(dto -> ExperienceLikesResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ExperienceLikesResponse retrieveById(Long experienceLikesId) {

    return experienceLikesRepository
            .retrieveById(experienceLikesId)
            .map(dto -> profileLoader(ExperienceLikesResponse.fromDto(dto)))
            .get();
  }

  public List<ExperienceLikesResponse> search(final String searchString) {

    return experienceLikesRepository
            .search(searchString)
            .stream()
            .map(dto -> profileLoader(ExperienceLikesResponse.fromDto(dto)))
            .collect(Collectors.toList());
  }

    public Integer getLikesCountOfProfile(Long profileId) {
      return experienceLikesRepository.getLikesCountOfProfile(profileId);
    }
  private ExperienceLikesResponse profileLoader(ExperienceLikesResponse response) {
    if (response.getLikerProfile() != null
            && response.getLikerProfile().getProfileId() != null) {
      response.getLikerProfile().cloneProperties(
              profileService.retrieveProfileDtoById(
                      Long.valueOf(response.getLikerProfile().getProfileId())));
    }
    return response;
  }
}
