package com.vire.repository;

import com.vire.dao.FeedsDao;
import com.vire.dao.FeedsSendToDao;
import com.vire.dto.FeedsSendToDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedsSendToRepository {

    @Autowired
    FeedsSendToRepositoryJpa feedsSendToRepositoryJpa;

    public FeedsSendToDto save(final FeedsSendToDto feedsSendToDto) {
        var feedsSendToDao = FeedsSendToDao.fromDto(feedsSendToDto);
        feedsSendToDao.onPrePersist();
        return feedsSendToRepositoryJpa.save(feedsSendToDao).toDto();
    }
    public FeedsSendToDto update(final FeedsSendToDto feedsSendToDto) {
        var existingObject = feedsSendToRepositoryJpa.findById(feedsSendToDto.getFeedsSendToId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }
        var feedsSendToDao = FeedsSendToDao.fromDto(feedsSendToDto);
        feedsSendToDao.onPreUpdate();
        return feedsSendToRepositoryJpa.save(feedsSendToDao).toDto();
    }

    public Optional<FeedsSendToDto> deleteSent(final Long sentId) {

        var optionalFeedsComment = retrieveById(sentId);
        if (optionalFeedsComment.isPresent()) {
            feedsSendToRepositoryJpa.deleteById(sentId);
        } else {
            throw new RuntimeException("Feeds Post sent Object not exists in DB");
        }

        return optionalFeedsComment;
    }

    public Optional<FeedsSendToDto> retrieveById(Long commentId) {

        return feedsSendToRepositoryJpa.findById(commentId).map(dao -> dao.toDto());
    }
    public List<FeedsSendToDto> searchSent(final String searchString) {

        var spec = new CustomSpecificationResolver<FeedsSendToDao>(searchString).resolve();

        return feedsSendToRepositoryJpa.findAll(spec).stream().map(dao -> dao.toDto()).collect(Collectors.toList());
    }

}
