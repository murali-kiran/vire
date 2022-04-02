package com.vire.service;

import com.vire.exception.FileStorageException;
import com.vire.model.request.SocialImageRequest;
import com.vire.model.request.SocialRequest;
import com.vire.model.response.SocialImageResponse;
import com.vire.model.response.SocialResponse;
import com.vire.repository.SocialImageRepository;
import com.vire.repository.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialService {

    @Autowired
    SocialRepository socialRepo;

    @Autowired
    SocialImageRepository socialImageRepo;

    public SocialResponse createSocial(final SocialRequest request) {

        var dto = request.toDto();

        return SocialResponse.fromDto(socialRepo.createSocial(dto));
    }

    public SocialResponse updateSocial(final SocialRequest request) {

        var dto = request.toDto();

        return SocialResponse.fromDto(socialRepo.updateSocial(dto));
    }

    public SocialResponse deleteSocialPost(final Long socialId) {

        return socialRepo.deleteSocialPost(socialId)
                .map(dto -> SocialResponse.fromDto(dto))
                .get();
    }
    public List<SocialResponse> getSocials() {

        return socialRepo.getAllSocials().stream()
                .map(dto -> SocialResponse.fromDto(dto))
                .collect(Collectors.toList());

    }
    public SocialResponse retrieveById(Long socialId) {

        return socialRepo.retrieveById(socialId)
                .map(dto -> SocialResponse.fromDto(dto))
                .get();
    }

    public List<SocialResponse> getPostsByCommunity(Long communityId) {

        return socialRepo.getPostsByCommunity(communityId).stream()
                .map(dto -> SocialResponse.fromDto(dto))
                .collect(Collectors.toList());

    }

    public List<SocialResponse> search(final String searchString) {

        return socialRepo.search(searchString).stream()
                .map(dto -> SocialResponse.fromDto(dto))
                .collect(Collectors.toList());
    }

    public SocialImageResponse uploadImage(final SocialImageRequest request) {

        var dto = request.toDto();

        return SocialImageResponse.fromDto(socialImageRepo.uploadImage(dto));
    }

    public String storeFile(MultipartFile file, String filePath) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        final Path fileStorageLocation;
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path pathDir = Paths.get(filePath).toAbsolutePath().normalize();
            Files.createDirectories(pathDir);
            Path targetLocation = pathDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.toString();
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        } catch (Exception ex){
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
