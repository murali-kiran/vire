package com.vire.repository;

import com.vire.dao.SocialDao;
import com.vire.dao.SocialPostChatDao;
import com.vire.dto.SocialPostChatDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialPostChatRepository {

    @Autowired
    SocialPostChatRepositoryJpa socialPostChatRepositoryJpa;

    public SocialPostChatDto create(final SocialPostChatDto socialPostChatDto) {
        return socialPostChatRepositoryJpa.save(SocialPostChatDao.fromDto(socialPostChatDto)).toDto();
    }

    public SocialPostChatDto update(final SocialPostChatDto socialPostChatDto) {
        var existingObject = socialPostChatRepositoryJpa.findById(socialPostChatDto.getId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }

        return socialPostChatRepositoryJpa.save(SocialPostChatDao.fromDto(socialPostChatDto)).toDto();
    }

    public Optional<SocialPostChatDto> delete(final Long chatId) {

        var optionalSocial = retrieveById(chatId);

        if (optionalSocial.isPresent()) {
            socialPostChatRepositoryJpa.deleteById(chatId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocial;
    }
    public List<SocialPostChatDto> getAll() {

        return socialPostChatRepositoryJpa.findAll()
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
    public Optional<SocialPostChatDto> retrieveById(Long chatId) {

        return socialPostChatRepositoryJpa.findById(chatId).map(dao -> dao.toDto());
    }

    public List<SocialPostChatDto> search(final String searchString) {
        var spec = new CustomSpecificationResolver<SocialPostChatDao>(searchString).resolve();

        return socialPostChatRepositoryJpa.findAll(spec).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
}
