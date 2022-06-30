package com.vire.service;

import com.vire.model.request.LikesRequest;
import com.vire.model.response.CommentResponse;
import com.vire.model.response.LikesResponse;
import com.vire.repository.LikesRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesService {
    @Autowired
    Snowflake snowflake;

    @Autowired
    LikesRepository likesRepository;
    @Autowired
    ProfileService profileService;

    public LikesResponse createLike(final LikesRequest request) {

        var dto = request.toDto();
        dto.setId(snowflake.nextId());
        return LikesResponse.fromDto(likesRepository.createLike(dto));
    }

    public LikesResponse updateLike(final LikesRequest request) {

        var dto = request.toDto();

        return LikesResponse.fromDto(likesRepository.updateLike(dto));
    }

    public LikesResponse deleteLike(final Long likeId) {

        return likesRepository.deleteLike(likeId)
                .map(dto -> LikesResponse.fromDto(dto))
                .get();
    }

    public LikesResponse retrieveById(Long likeId) {

        return likesRepository.retrieveById(likeId)
                .map(dto -> profileLoader(LikesResponse.fromDto(dto)))
                .get();
    }

    public List<LikesResponse> searchLikes(final String searchString) {

        return likesRepository.searchLikes(searchString).stream()
                .map(dto -> profileLoader(LikesResponse.fromDto(dto)))
                .collect(Collectors.toList());
    }
    private LikesResponse profileLoader(LikesResponse response){
        if (response.getLikerProfile() != null
                && response.getLikerProfile().getProfileId() != null) {
            response.getLikerProfile().cloneProperties(
                    profileService.retrieveProfileDtoById(
                            Long.valueOf(response.getLikerProfile().getProfileId())));
        }
        return response;
    }
}
