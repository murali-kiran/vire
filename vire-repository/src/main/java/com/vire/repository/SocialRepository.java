package com.vire.repository;

import com.vire.dao.*;
import com.vire.dao.view.SocialViewDao;
import com.vire.dto.SocialDto;
import com.vire.dto.SocialSendToDto;
import com.vire.model.response.PageWiseSearchResponse;
import com.vire.repository.search.CustomSpecificationResolver;
import com.vire.repository.view.SocialViewRepositoryJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SocialRepository {

    @Autowired
    SocialRepositoryJpa socialRepositoryJpa;

    @Autowired
    SocialViewRepositoryJpa socialViewRepositoryJpa;

    @Autowired
    NotificationRepository notificationRepository;



    @Autowired
    SocialFileRepositoryJpa socialFileRepositoryJpa;

   /* @PersistenceContext
    EntityManager entityManager;*/

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
        var files = socialFileRepositoryJpa.findBySocialId(socialDto.getSocialId());
        var fileIds = files.stream().map(f -> f.getSocialFileId()).collect(Collectors.toList());
        for(var fileid: fileIds){
            socialFileRepositoryJpa.deleteById(fileid);
        }
        var existingObject = socialRepositoryJpa.findById(socialDto.getSocialId());

        if (existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }
        //var socialDao = SocialDao.fromDto(socialDto);
        SocialDao socialDao = existingObject.get();

        /*if (!CollectionUtils.isEmpty(socialDao.getSendTo())) {
            for (var sendToDto : socialDao.getSendTo()) {
                sendToDto.setSocial(socialDao);
                sendToDto.onPreUpdate();

            }
        }*/

        if(socialDto.getSubject() != null){
            socialDao.setSubject(socialDto.getSubject());
        }
        if(socialDto.getDescription() != null){
            socialDao.setDescription(socialDto.getDescription());
        }
        if(socialDto.getAlternateContact() != null){
            socialDao.setAlternateContact(socialDto.getAlternateContact());
            socialDao.setContact(null);
        }
        if(socialDto.getContact() != null){
            socialDao.setAlternateContact(null);
            socialDao.setContact(socialDto.getContact());
        }
        if (socialDto.getSocialFileList() != null && !socialDto.getSocialFileList().isEmpty()) {
            socialDao.setSocialFileList(socialDto.getSocialFileList()
                    .stream()
                    .map(socialFileDto -> SocialFileDao.fromDto(socialFileDto))
                    .collect(Collectors.toList())
            );
        }
        if (!CollectionUtils.isEmpty(socialDao.getSocialFileList())) {
            for (var socialFile : socialDao.getSocialFileList()) {
                socialFile.setSocial(socialDao);
                socialFile.onPrePersist();
            }
        }
        socialDao.onPreUpdate();
        return socialRepositoryJpa.save(socialDao).toDto();
    }

    @Transactional
    public Optional<SocialDto> deleteSocialPost(final Long socialId) {

        var optionalSocial = retrieveById(socialId);
        log.info("Delete Social ID#########:"+socialId);
        if (optionalSocial.isPresent()) {
            /*likesRepositoryJpa.deleteBySocialId(socialId);
            commentReplyReposJpa.deleteBySocialId(socialId);
            commentRepositoryJpa.deleteBySocialId(socialId);
            socialChatRepositoryJpa.deleteBySocialId(socialId);
            socialCallRequestRepositoryJpa.deleteBySocialId(socialId);
            socialReportRepositoryJpa.deleteBySocialId(socialId);
            socialRepositoryJpa.deleteById(socialId);*/
            socialRepositoryJpa.updateDeletedTime(Instant.now().toEpochMilli(), socialId);
            updateNotifications(socialId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocial;
    }

    private void updateNotifications(Long socialId) {
        try{
        var notificationsIdList = notificationRepository.retrieveBySocial(socialId);
        for (Long notificationId:notificationsIdList) {
            notificationRepository.updateDeletedTime(notificationId);
        }
        }catch(Exception e){
            throw new RuntimeException("Deleting-- updating delete time cause issues: "+e.getMessage());
        }
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

        return socialRepositoryJpa.findAll(spec, Sort.by(Sort.Direction.DESC, "updatedTime")).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

   /* public List<SocialDto> getAllBySendToInterestType(List<String> interests) {
        TypedQuery<SocialDto> query
                = entityManager.createQuery(
                "SELECT s FROM SocialDto s JOIN s.SocialSendToDto d WHERE d.type=d.value IN", SocialDto.class);
        List<SocialDto> resultList = query.getResultList();
        return resultList;
    }*/
    public List<SocialDto> retrieveByCommunity(Long communityId){
        return socialRepositoryJpa.findByCommunity(communityId).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
    
    
    public PageWiseSearchResponse<SocialDto> getAllPaged(Integer pageNumber, Integer pageSize) {

        PageWiseSearchResponse<SocialDto> response = new PageWiseSearchResponse<>();
        PageRequest request = PageRequest.of(pageNumber-1 , pageSize);

        Page<SocialViewDao> page = socialViewRepositoryJpa.findByDeletedTimeIsNull(request);
        List<SocialDto> socialDtos = page.stream().parallel().map(dao -> dao.toDto())
                .collect(Collectors.toList());

        response.setPageCount(page.getTotalPages());
        response.setList(socialDtos);

        return response;
    }
}
