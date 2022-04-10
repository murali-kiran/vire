package com.vire.repository;

import com.vire.dao.FileDao;
import com.vire.dao.SocialDao;
import com.vire.dto.FileDto;
import com.vire.dto.SocialDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileRepository {

    @Autowired
    FileRepositoryJpa fileRepositoryJpa;

    public FileDto uploadFile(final FileDto fileDto) {
        return fileRepositoryJpa.save(FileDao.fromDto(fileDto)).toDto();
    }

    public Optional<FileDto> retrieveById(Long socialId) {

        return fileRepositoryJpa.findById(socialId).map(dao -> dao.toDto());
    }
    public Optional<FileDto> deleteFile(final Long fileId) {

        var optionalSocial = retrieveById(fileId);

        if (optionalSocial.isPresent()) {
            fileRepositoryJpa.deleteById(fileId);
        } else {
            throw new RuntimeException("File Object not exists in DB with id "+fileId);
        }

        return optionalSocial;
    }

}
