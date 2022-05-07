package com.vire.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vire.amazon.BucketName;
import com.vire.exception.FileStorageException;
import com.vire.model.request.FileRequest;
import com.vire.model.response.FileResponse;
import com.vire.repository.FileRepository;
import com.vire.utils.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class FileService {
    @Autowired
    Snowflake snowflake;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    AmazonS3 amazonS3;

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
    private void storeInAws(MultipartFile file, String fileName, String fileDirName){
        try {
            String path = String.format("%s/%s", BucketName.TODO_IMAGE.getBucketName(), fileDirName);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.addUserMetadata("title", file.getName());
            PutObjectRequest request = new PutObjectRequest(path, fileName, file.getInputStream(), objectMetadata);
            request.setMetadata(objectMetadata);
            amazonS3.putObject(request);
            log.info("**********************File Uploaded successfully**********************Content type" + objectMetadata.getContentType()+"**File Name**"+fileName);
        } catch (AmazonServiceException e) {
            log.info("**********************File Upload Failed*******************************");
            throw new IllegalStateException("Failed to upload the file", e);
        } catch (SdkClientException e) {
            log.info("**********************File Upload Failed*******************************");
            throw new IllegalStateException("Failed to upload the file", e);
        } catch (Exception e) {
            log.info("**********************File Upload Failed*******************************");
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    public String storeFile(MultipartFile file, String filePath, String fileDirName, Boolean isAmazonServer) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String relativePath = fileDirName+"/"+fileName;;
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            if(isAmazonServer) {
                storeInAws(file, fileName, fileDirName);
            }else{
                // Copy file to the target location (Replacing existing file with the same name)
                Path pathDir = Paths.get(filePath, fileDirName).toAbsolutePath().normalize();
                Files.createDirectories(pathDir);
                Path targetLocation = pathDir.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            return relativePath;
        } catch (IOException ex) {
            log.info("**********************File Upload Failed*******************************");
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        } catch (Exception ex){
            log.info("**********************File Upload Failed*******************************");
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
