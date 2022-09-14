package com.vire.repository;

import com.vire.dao.SocialChatDao;
import com.vire.dto.SocialChatDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialChatRepository {

    @Autowired
    SocialChatRepositoryJpa socialPostChatRepositoryJpa;

    public SocialChatDto create(final SocialChatDto socialChatDto) {
        var socialChatDao = SocialChatDao.fromDto(socialChatDto);
        socialChatDao.onPrePersist();
        return socialPostChatRepositoryJpa.save(socialChatDao).toDto();
    }

    public SocialChatDto update(final SocialChatDto socialChatDto) {
        var existingObject = socialPostChatRepositoryJpa.findById(socialChatDto.getId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }
        var socialChatDao = SocialChatDao.fromDto(socialChatDto);
        socialChatDao.onPreUpdate();
        return socialPostChatRepositoryJpa.save(socialChatDao).toDto();
    }

    public Optional<SocialChatDto> delete(final Long chatId) {

        var optionalSocial = retrieveById(chatId);

        if (optionalSocial.isPresent()) {
            socialPostChatRepositoryJpa.deleteById(chatId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocial;
    }
    public List<SocialChatDto> getAll() {

        return socialPostChatRepositoryJpa.findAll()
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
    public Optional<SocialChatDto> retrieveById(Long chatId) {

        return socialPostChatRepositoryJpa.findById(chatId).map(dao -> dao.toDto());
    }

    public List<SocialChatDto> search(final String searchString) {
        var spec = new CustomSpecificationResolver<SocialChatDao>(searchString).resolve();

        return socialPostChatRepositoryJpa.findAll(spec, Sort.by(Sort.Direction.DESC, "createdTime")).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
}
