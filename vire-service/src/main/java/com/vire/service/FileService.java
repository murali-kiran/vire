package com.vire.service;

import com.vire.exception.FileStorageException;
import com.vire.model.request.FileRequest;
import com.vire.model.response.FileResponse;
import com.vire.repository.FileRepository;
import com.vire.utils.Snowflake;
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
public class FileService {
    @Autowired
    Snowflake snowflake;

    @Autowired
    FileRepository fileRepository;

    public FileResponse uploadFile(final FileRequest request) {

        var dto = request.toDto();
        dto.setFileId(snowflake.nextId());

        return FileResponse.fromDto(fileRepository.uploadFile(dto));
    }
    public FileResponse deleteFile(final Long socialId) {

        return fileRepository.deleteFile(socialId)
                .map(dto -> FileResponse.fromDto(dto))
                .get();
    }

    public FileResponse retrieveById(Long socialId) {

        return fileRepository.retrieveById(socialId)
                .map(dto -> FileResponse.fromDto(dto))
                .get();
    }

    public String storeFile(MultipartFile file, String filePath, String fileDirType) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        final Path fileStorageLocation;
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path pathDir = Paths.get(filePath, fileDirType).toAbsolutePath().normalize();
            Files.createDirectories(pathDir);
            Path targetLocation = pathDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String relativePath = Paths.get(fileDirType).resolve(fileName).toString();
            return relativePath;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        } catch (Exception ex){
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
