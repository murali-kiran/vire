package com.vire.service;
//
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
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

@Service
public class FileService {
    @Autowired
    Snowflake snowflake;

    @Autowired
    FileRepository fileRepository;
  /*  @Autowired
    AmazonS3 amazonS3;*/

    public FileResponse uploadFile(final FileRequest request) {

        var dto = request.toDto(snowflake);

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
        boolean amazonServer = false;
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

            if(amazonServer) {
                /*try {
                    Map<String, String> metadata = new HashMap<>();
                    metadata.put("Content-Type", file.getContentType());
                    metadata.put("Content-Length", String.valueOf(file.getSize()));
                    String path = String.format("%s/%s", "BucketName", UUID.randomUUID());
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    for (var entrySet:
                         metadata.entrySet()) {
                        objectMetadata.addUserMetadata(entrySet.getKey(),entrySet.getValue());
                    }
                    amazonS3.putObject(path, fileName, file.getInputStream(), objectMetadata);
                } catch (AmazonServiceException e) {
                    throw new IllegalStateException("Failed to upload the file", e);
                }*/
            }else{
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }
            String relativePath = Paths.get(fileDirType).resolve(fileName).toString();
            return relativePath;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        } catch (Exception ex){
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
