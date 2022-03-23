package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ProfileRequest;
import com.vire.model.response.ProfileResponse;
import com.vire.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.PROFILE_REQUEST_PATH_API)
public class ProfileController {

  @Autowired ProfileService profileService;

  @PostMapping
  public ResponseEntity<ProfileResponse> createProfile(@RequestBody ProfileRequest request) {
    return new ResponseEntity<>(profileService.createProfile(request), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<ProfileResponse> updateProfile(@RequestBody ProfileRequest request) {
    return new ResponseEntity<>(profileService.updateProfile(request), HttpStatus.OK);
  }

  @DeleteMapping("/{profileid}")
  public ResponseEntity<ProfileResponse> deleteProfile(
      @PathVariable(value = "profileid") Long profileId) {
    return new ResponseEntity<>(profileService.deleteProfile(profileId), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ProfileResponse>> retrieveAllProfiles() {
    return new ResponseEntity<>(profileService.retrieveAllProfiles(), HttpStatus.OK);
  }

  @GetMapping("/{profileid}")
  public ResponseEntity<ProfileResponse> retrieveProfileById(
      @PathVariable(value = "profileid") Long profileId) {
    return new ResponseEntity<>(profileService.retrieveProfileById(profileId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ProfileResponse>> searchProfiles(
      @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(profileService.searchProfiles(searchString), HttpStatus.OK);
  }
}
