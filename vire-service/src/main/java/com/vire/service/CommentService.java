package com.vire.service;

import com.vire.model.request.CommentRequest;
import com.vire.model.response.CommentResponse;
import com.vire.repository.CommentRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    Snowflake snowflake;

    @Autowired
    CommentRepository commentRepository;

    public CommentResponse createComment(final CommentRequest request) {

        var dto = request.toDto();
        dto.setId(snowflake.nextId());

        return CommentResponse.fromDto(commentRepository.createComment(dto));
    }

    public CommentResponse updateComment(final CommentRequest request) {

        var dto = request.toDto();

        return CommentResponse.fromDto(commentRepository.updateComment(dto));
    }

    public CommentResponse deleteComment(final Long chatId) {

        return commentRepository.deleteComment(chatId)
                .map(dto -> CommentResponse.fromDto(dto))
                .get();
    }
   
    public CommentResponse retrieveById(Long chatId) {

        return commentRepository.retrieveById(chatId)
                .map(dto -> CommentResponse.fromDto(dto))
                .get();
    }

    public List<CommentResponse> searchComments(final String searchString) {

        return commentRepository.searchComments(searchString).stream()
                .map(dto -> CommentResponse.fromDto(dto))
                .collect(Collectors.toList());
    }
}
