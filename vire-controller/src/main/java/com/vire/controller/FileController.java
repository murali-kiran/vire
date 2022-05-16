package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FileRequest;
import com.vire.model.response.FileResponse;
import com.vire.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(VireConstants.FILE_REQUEST_PATH_API)
public class FileController {

    @Autowired
    FileService fileService;
    @Value("${file.path}")
    private String filePath;
    @Value("${file.type}")
    private String fileDirName;


    @PostMapping(value = "/uploadFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<FileResponse> create(@RequestPart("file") MultipartFile file){
        FileRequest request = new FileRequest();
        String filePath = fileService.storeFile(file, this.filePath, this.fileDirName, VireConstants.IS_AMAZON_SERVER);
        request.setFileSize(file.getSize());
        request.setMimeType(file.getContentType());
        request.setFileCommonPath(filePath);
        return new ResponseEntity<>(fileService.uploadFile(request), HttpStatus.CREATED);
    }
    @DeleteMapping("/{fileid}")
    public ResponseEntity<FileResponse> delete(
            @PathVariable(value = "fileid") Long chatId) {
        return new ResponseEntity<>(fileService.deleteFile(chatId), HttpStatus.OK);
    }
    @GetMapping("/{fileid}")
    public ResponseEntity<FileResponse> retrieveById(@PathVariable Long fileId){
        log.info("*******File ID**********:"+fileId);
        return new ResponseEntity<FileResponse>(fileService.retrieveById(fileId), HttpStatus.OK);
    }

}
