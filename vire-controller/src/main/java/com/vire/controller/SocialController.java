package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialImageRequest;
import com.vire.model.request.SocialRequest;
import com.vire.model.response.SocialImageResponse;
import com.vire.model.response.SocialResponse;
import com.vire.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping(VireConstants.SOCIAL_REQUEST_PATH_API)
public class SocialController {

    @Autowired
    SocialService socialService;
    @Value("${image.path}")
    private String imagePath;

    @PostMapping("/create")
    public ResponseEntity<SocialResponse> create(@RequestBody SocialRequest request){
        return new ResponseEntity<>(socialService.createSocial(request), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<SocialResponse> update(@RequestBody SocialRequest request){
        return new ResponseEntity<>(socialService.updateSocial(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{socialid}")
    public ResponseEntity<SocialResponse> deleteProfile(
            @PathVariable(value = "socialid") Long profileId) {
        return new ResponseEntity<>(socialService.deleteSocialPost(profileId), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<SocialResponse>> retrieveAll(){
        return new ResponseEntity<>(socialService.getSocials(), HttpStatus.OK);
    }
    @GetMapping("/{socialid}")
    public ResponseEntity<SocialResponse> retrieveById(@PathVariable Long socialPostId){
        return new ResponseEntity<SocialResponse>(socialService.retrieveById(socialPostId), HttpStatus.OK);
    }
    @GetMapping("/{communityid}/communityposts")
    public ResponseEntity<List<SocialResponse>> getPostsByCommunity(@PathVariable(name="communityid") Long communityId){
        return new ResponseEntity<>(socialService.getPostsByCommunity(communityId), HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<List<SocialResponse>> search(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(socialService.search(searchString), HttpStatus.OK);
    }
    @PostMapping(value = "/uploadImage", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<SocialImageResponse> create(@RequestPart("file") MultipartFile file){
        SocialImageRequest request = new SocialImageRequest();
        String filePath = socialService.storeFile(file, imagePath);
        request.setImageSize(file.getSize());
        request.setMimeType(file.getContentType());
        request.setImagePath(filePath);
        return new ResponseEntity<>(socialService.uploadImage(request), HttpStatus.CREATED);
    }
}
