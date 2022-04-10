package com.vire.service;

import com.vire.model.request.LikesRequest;
import com.vire.model.response.LikesResponse;
import com.vire.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesService {

    @Autowired
    LikesRepository likesRepository;

    public LikesResponse createLike(final LikesRequest request) {

        var dto = request.toDto();

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
                .map(dto -> LikesResponse.fromDto(dto))
                .get();
    }

    public List<LikesResponse> searchLikes(final String searchString) {

        return likesRepository.searchLikes(searchString).stream()
                .map(dto -> LikesResponse.fromDto(dto))
                .collect(Collectors.toList());
    }
}
