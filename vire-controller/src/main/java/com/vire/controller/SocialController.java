package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialRequest;
import com.vire.model.response.SocialPostResponse;
import com.vire.model.response.SocialResponse;
import com.vire.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.SOCIAL_REQUEST_PATH_API)
public class SocialController {
    @Autowired
    SocialService socialService;

    @PostMapping("/create")
    public ResponseEntity<SocialResponse> create(@Valid @RequestBody SocialRequest request) {
        return new ResponseEntity<>(socialService.createSocial(request), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<SocialResponse> update(@Valid @RequestBody SocialRequest request) {
        return new ResponseEntity<>(socialService.updateSocial(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{socialid}")
    public ResponseEntity<SocialResponse> delete(
            @PathVariable(value = "socialid") Long socialId) {
        return new ResponseEntity<>(socialService.deleteSocialPost(socialId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SocialResponse>> retrieveAll() {
        return new ResponseEntity<>(socialService.getSocials(), HttpStatus.OK);
    }

    @GetMapping("/{socialid}")
    public ResponseEntity<SocialResponse> retrieveById(@PathVariable(name = "socialid") Long socialId) {
        return new ResponseEntity<SocialResponse>(socialService.retrieveById(socialId), HttpStatus.OK);
    }

    @GetMapping("/{communityid}/communityposts")
    public ResponseEntity<List<SocialResponse>> getPostsByCommunity(@PathVariable(name = "communityid") Long communityId) {
        return new ResponseEntity<>(socialService.getPostsByCommunity(communityId), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<SocialResponse>> search(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(socialService.search(searchString), HttpStatus.OK);
    }

    @GetMapping("socialdetails/{socialid}")
    public ResponseEntity<SocialPostResponse> findById(@PathVariable(name = "socialid") Long socialPostId) {
        return new ResponseEntity<SocialPostResponse>(socialService.retrieveSocialDetailsById(socialPostId), HttpStatus.OK);
    }
    @GetMapping("socialposts/{profileid}")
    public ResponseEntity<List<SocialPostResponse>> findByIdProfileId(@PathVariable(name = "profileid") Long profileId) {
        return new ResponseEntity<List<SocialPostResponse>>(socialService.retrievePostsByProfileId(profileId), HttpStatus.OK);
    }
}
