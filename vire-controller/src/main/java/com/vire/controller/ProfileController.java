package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FirmRequest;
import com.vire.model.request.PersonalRequest;
import com.vire.model.response.FirmResponse;
import com.vire.model.response.PersonalResponse;
import com.vire.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProfileController {

  @Autowired ProfileService profileService;

  @PostMapping(VireConstants.FIRM_PROFILE_REQUEST_PATH_API)
  public ResponseEntity<FirmResponse> createFirmProfile(@RequestBody FirmRequest request) {
    return new ResponseEntity<>(profileService.createFirmProfile(request), HttpStatus.CREATED);
  }

  @PostMapping(VireConstants.PERSONAL_PROFILE_REQUEST_PATH_API)
  public ResponseEntity<PersonalResponse> createPersonalProfile(@RequestBody PersonalRequest request) {
    return new ResponseEntity<>(profileService.createPersonalProfile(request), HttpStatus.CREATED);
  }

  @PutMapping(VireConstants.PERSONAL_PROFILE_REQUEST_PATH_API)
  public ResponseEntity<PersonalResponse> updatePersonalProfile(@RequestBody PersonalRequest request) {
    return new ResponseEntity<>(profileService.updatePersonalProfile(request), HttpStatus.OK);
  }

  @PutMapping(VireConstants.FIRM_PROFILE_REQUEST_PATH_API)
  public ResponseEntity<FirmResponse> updateFirmProfile(@RequestBody FirmRequest request) {
    return new ResponseEntity<>(profileService.updateFirmProfile(request), HttpStatus.OK);
  }

  @DeleteMapping(VireConstants.PERSONAL_PROFILE_REQUEST_PATH_API+"/{profileid}")
  public ResponseEntity<PersonalResponse> deletePersonalProfile(
      @PathVariable(value = "profileid") Long profileId) {
    Optional<PersonalResponse> profileResponse = profileService.deletePersonalProfile(profileId);
    return profileResponse.isPresent() ? new ResponseEntity<>(profileResponse.get(), HttpStatus.OK): new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(VireConstants.FIRM_PROFILE_REQUEST_PATH_API+"/{profileid}")
  public ResponseEntity<FirmResponse> deleteFirmProfile(@PathVariable(value = "profileid") Long profileId) {
    Optional<FirmResponse> firmResponse = profileService.deleteFirmProfile(profileId);
    return firmResponse.isPresent() ? new ResponseEntity<>(firmResponse.get(), HttpStatus.OK): new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(VireConstants.PERSONAL_PROFILE_REQUEST_PATH_API)
  public ResponseEntity<List<PersonalResponse>> retrieveAllProfiles() {
    return new ResponseEntity<>(profileService.retrieveAllProfiles(), HttpStatus.OK);
  }

  @GetMapping(VireConstants.PERSONAL_PROFILE_REQUEST_PATH_API+"/{profileid}")
  public ResponseEntity<PersonalResponse> retrievePersonalProfileById(@PathVariable(value = "profileid") Long profileId) {
    Optional<PersonalResponse> profileResponse = profileService.retrievePersonalProfileById(profileId);
    return profileResponse.isPresent() ? new ResponseEntity<>(profileResponse.get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @GetMapping(VireConstants.FIRM_PROFILE_REQUEST_PATH_API+"/{profileid}")
  public ResponseEntity<FirmResponse> retrieveFirmProfileById(@PathVariable(value = "profileid") Long profileId) {
    Optional<FirmResponse> firmResponse = profileService.retrieveFirmProfileById(profileId);
    return firmResponse.isPresent() ? new ResponseEntity<>(firmResponse.get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @GetMapping("search")
  public ResponseEntity<List<PersonalResponse>> searchProfiles(
      @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(profileService.searchProfiles(searchString), HttpStatus.OK);
  }
}
