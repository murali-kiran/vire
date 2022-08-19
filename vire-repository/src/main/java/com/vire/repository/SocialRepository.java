package com.vire.repository;

import com.vire.dao.SocialDao;
import com.vire.dao.SocialReportDao;
import com.vire.dao.SocialSendToDao;
import com.vire.dto.SocialDto;
import com.vire.dto.SocialSendToDto;
import com.vire.repository.search.CustomSpecificationResolver;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class SocialRepository {

    @Autowired
    SocialRepositoryJpa socialRepositoryJpa;
    @Autowired
    SocialReportRepositoryJpa socialReportRepositoryJpa;
    @Autowired
    LikesRepositoryJpa likesRepositoryJpa;
    @Autowired
    CommentRepositoryJpa commentRepositoryJpa;
    @Autowired
    CommentReplyReposJpa commentReplyReposJpa;
    @Autowired
    SocialChatRepositoryJpa socialChatRepositoryJpa;

    @PersistenceContext
    private EntityManager entityManager;
    public SocialDto createSocial(final SocialDto socialDto) {
        var socialDao = SocialDao.fromDto(socialDto);

        if (!CollectionUtils.isEmpty(socialDao.getSendTo())) {
            for (var sendToDto : socialDao.getSendTo()) {
                sendToDto.setSocial(socialDao);
                sendToDto.onPrePersist();
            }
        }
        if (!CollectionUtils.isEmpty(socialDao.getSocialFileList())) {
            for (var socialFile : socialDao.getSocialFileList()) {
                socialFile.setSocial(socialDao);
                socialFile.onPrePersist();
            }
        }
        socialDao.onPrePersist();
        log.info("social object:::" + socialDao);
        return socialRepositoryJpa.save(socialDao).toDto();
    }

    public SocialDto updateSocial(final SocialDto socialDto) {
        var existingObject = socialRepositoryJpa.findById(socialDto.getSocialId());

        if (existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }
        var socialDao = SocialDao.fromDto(socialDto);
        socialDao.onPreUpdate();
        if (!CollectionUtils.isEmpty(socialDao.getSendTo())) {
            for (var sendToDto : socialDao.getSendTo()) {
                sendToDto.setSocial(socialDao);
                sendToDto.onPreUpdate();

            }
        }
        return socialRepositoryJpa.save(socialDao).toDto();
    }
    @Transactional
    public Optional<SocialDto> deleteSocialPost(final Long socialId) {

        var optionalSocial = retrieveById(socialId);
        log.info("Delete Social ID#########:"+socialId);
        if (optionalSocial.isPresent()) {
            likesRepositoryJpa.deleteBySocialId(socialId);
            commentReplyReposJpa.deleteBySocialId(socialId);
            commentRepositoryJpa.deleteBySocialId(socialId);
            socialChatRepositoryJpa.deleteBySocialId(socialId);
            socialReportRepositoryJpa.deleteBySocialId(socialId);
            socialRepositoryJpa.deleteById(socialId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocial;
    }

    public List<SocialDto> getAllSocials() {

        return socialRepositoryJpa.findAll(Sort.by(Sort.Direction.DESC, "updatedTime"))
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

    public Optional<SocialDto> retrieveById(Long socialId) {

        return socialRepositoryJpa.findById(socialId).map(dao -> dao.toDto());
    }

    public List<SocialDto> getPostsByCommunity(Long communityId) {

        return socialRepositoryJpa.findByCategoryId(communityId)
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

    public List<SocialDto> search(final String searchString) {

        var spec = new CustomSpecificationResolver<SocialDao>(searchString).resolve();

        return socialRepositoryJpa.findAll(spec).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

    public List<SocialDto> getAllBySendToInterestType(List<String> interests) {
        TypedQuery<SocialDto> query
                = entityManager.createQuery(
                "SELECT s FROM SocialDto s JOIN s.SocialSendToDto d WHERE d.type=d.value IN", SocialDto.class);
        List<SocialDto> resultList = query.getResultList();
        return resultList;
    }
    public List<SocialDto> retrieveByCommunity(Long communityId){
        return socialRepositoryJpa.findByCommunity(communityId).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
}
