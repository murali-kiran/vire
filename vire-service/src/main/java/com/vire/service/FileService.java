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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileService {
    @Autowired
    Snowflake snowflake;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    AmazonS3 amazonS3;
    @Value("${server.image.path}")
    private String imagePath;

    public FileResponse uploadFile(final FileRequest request) {

        var dto = request.toDto(snowflake);

        return FileResponse.fromDto(fileRepository.uploadFile(dto), imagePath);
    }
    public FileResponse deleteFile(final Long socialId) {

        return fileRepository.deleteFile(socialId)
                .map(dto -> FileResponse.fromDto(dto, imagePath))
                .get();
    }

    public FileResponse retrieveById(Long fileId) {

        return fileRepository.retrieveById(fileId)
                .map(dto -> FileResponse.fromDto(dto, imagePath))
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
        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        log.info("Uploaded file name:"+fileName);
        try {
            String fileNewName = String.valueOf(UUID.randomUUID()) + getFileExtension(fileName);
            if(isAmazonServer) {
                storeInAws(file, fileNewName, fileDirName);
            }else{
                // Copy file to the target location (Replacing existing file with the same name)
                Path pathDir = Paths.get(filePath, fileDirName).toAbsolutePath().normalize();
                Files.createDirectories(pathDir);
                Files.copy(file.getInputStream(), pathDir.resolve(fileNewName), StandardCopyOption.REPLACE_EXISTING);
            }
            log.info("Generated file name:"+fileNewName);
           return fileDirName + "/" + fileNewName;
        } catch (IOException ex) {
            log.info("**********************File Upload Failed*******************************");
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        } catch (Exception ex){
            log.info("**********************File Upload Failed*******************************");
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "txt"; // empty extension
        }
        return fileName.substring(lastIndexOf);
    }
    public ResponseEntity<Resource> retrieveFile(String fileCommonPath) {
        try {
            File file = new File(fileCommonPath);
            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpeg");
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);

        }catch (Exception ex){
            log.info("**********************File download Failed*******************************");
            throw new FileStorageException("Could not download file " + fileCommonPath + ". Please try again!", ex);
        }
    }
}
