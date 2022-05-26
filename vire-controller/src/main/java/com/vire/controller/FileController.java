package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FileRequest;
import com.vire.model.response.FileResponse;
import com.vire.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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


    @Operation(summary = "Upload File")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "File Uploading Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "File Uploading Failed",
                    content = @Content)})
    @PostMapping(value = "/uploadFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<FileResponse> create(@RequestPart("file") MultipartFile file){
        FileRequest request = new FileRequest();
        String filePath = fileService.storeFile(file, this.filePath, this.fileDirName, VireConstants.IS_AMAZON_SERVER);
        request.setFileSize(file.getSize());
        request.setMimeType(file.getContentType());
        request.setFileCommonPath(filePath);
        return new ResponseEntity<>(fileService.uploadFile(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete File by ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete File by ID Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Delete File by ID Failed",
                    content = @Content) })
    @DeleteMapping("/{fileid}")
    public ResponseEntity<FileResponse> delete(
            @PathVariable(value = "fileid") Long chatId) {
        return new ResponseEntity<>(fileService.deleteFile(chatId), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve File by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get File by ID Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Get File by ID Failed",
                    content = @Content) })
    @GetMapping("/{fileid}")
    public ResponseEntity<FileResponse> retrieveById(@PathVariable(value = "fileid") Long fileId){
        log.info("*******File ID**********:"+fileId);
        return new ResponseEntity<FileResponse>(fileService.retrieveById(fileId), HttpStatus.OK);
    }
    @GetMapping("/downloadFile/{fileid}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "fileid") Long fileid) {
            FileResponse fileResponse = fileService.retrieveById(fileid);
            String filePath = this.filePath + File.separator + fileResponse.getDownloadPath();
            return fileService.retrieveFile(filePath);
    }

}
