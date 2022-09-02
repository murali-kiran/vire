package com.vire.repository;

import com.vire.dao.FeedsDao;
import com.vire.dto.FeedsDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FeedsRepository {

    @Autowired
    FeedsRepositoryJpa feedsRepositoryJpa;
    @Autowired
    FeedsSendToRepository feedsSendToRepository;
    @PersistenceContext
    private EntityManager entityManager;
    public FeedsDto createFeeds(final FeedsDto feedsDto) {
        var feedsDao = FeedsDao.fromDto(feedsDto);

        if (!CollectionUtils.isEmpty(feedsDao.getFeedsSendTo())) {
            for (var sendToDto : feedsDao.getFeedsSendTo()) {
                sendToDto.setFeed(feedsDao);
                sendToDto.onPrePersist();
            }
        }
        feedsDao.onPrePersist();
        //feedsDao.getSendTo().get(0).getFeeds().setFeedsId(feedsDao.getFeedsId());
        System.out.println("sa:::" + feedsDao);
        return feedsRepositoryJpa.save(feedsDao).toDto();
    }

    public FeedsDto updateFeeds(final FeedsDto feedsDto) {
        var existingObject = feedsRepositoryJpa.findById(feedsDto.getFeedId());

        if (existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }
        var feedsDao = FeedsDao.fromDto(feedsDto);
        if (!CollectionUtils.isEmpty(feedsDao.getFeedsSendTo())) {
            for (var sendToDto : feedsDao.getFeedsSendTo()) {
                sendToDto.setFeed(feedsDao);
                sendToDto.onPreUpdate();
                sendToDto.setCreatedTime(existingObject.get().getCreatedTime());
            }
        }
        feedsDao.setCreatedTime(existingObject.get().getCreatedTime());
        feedsDao.onPreUpdate();
        return feedsRepositoryJpa.save(feedsDao).toDto();
    }

    @Transactional
    public Optional<FeedsDto> deleteFeedsPost(final Long feedId) {

        var optionalFeeds = retrieveById(feedId);
        List<FeedsDto> childFeeds = search("parentFeedId:"+feedId);
        if (optionalFeeds.isPresent()) {
            feedsRepositoryJpa.deleteById(feedId);
        } else {
            throw new RuntimeException("Feeds Post Object not exists in DB");
        }
        for(FeedsDto feedsDto : childFeeds){
            feedsRepositoryJpa.deleteById(feedsDto.getFeedId());
        }
        return optionalFeeds;
    }

    public List<FeedsDto> getAllFeeds() {

        return feedsRepositoryJpa.findAll(Sort.by(Sort.Direction.DESC, "updatedTime"))
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

    public Optional<FeedsDto> retrieveById(Long feedId) {

        return feedsRepositoryJpa.findById(feedId).map(dao -> dao.toDto());
    }


    public List<FeedsDto> search(final String searchString) {

        var spec = new CustomSpecificationResolver<FeedsDao>(searchString).resolve();

        return feedsRepositoryJpa.findAll(spec, Sort.by(Sort.Direction.DESC, "updatedTime")).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

    public List<FeedsDto> getAllBySendToInterestType(List<String> interests) {
        TypedQuery<FeedsDto> query
                = entityManager.createQuery(
                "SELECT s FROM FeedsDto s JOIN s.FeedsSendToDto d WHERE d.type=d.value IN", FeedsDto.class);
        List<FeedsDto> resultList = query.getResultList();
        return resultList;
    }
    public Integer countByParentFeedId(Long feedId) {
        return feedsRepositoryJpa.countByParentFeedId(feedId);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Set<Long> retrieveByCommunity(Long communityId){
        return feedsRepositoryJpa.findByCommunity(communityId);
    }

    public Set<Long> retrieveByChannel(Long channelId){
        return feedsRepositoryJpa.findByChannel(channelId);
    }
}
