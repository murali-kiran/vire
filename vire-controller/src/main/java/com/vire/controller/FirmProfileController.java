package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FirmRequest;
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
@RequestMapping(VireConstants.FIRM_PROFILE_REQUEST_PATH_API)
public class FirmProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping(value = "/create")
    public ResponseEntity<FirmResponse> createFirmProfile(@RequestBody FirmRequest request) {
        return new ResponseEntity<>(profileService.createFirmProfile(request), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<FirmResponse> updateFirmProfile(@RequestBody FirmRequest request) {
        return new ResponseEntity<>(profileService.updateFirmProfile(request), HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{profileid}")
    public ResponseEntity<FirmResponse> deleteFirmProfile(@PathVariable(value = "profileid") Long profileId) {
        Optional<FirmResponse> firmResponse = profileService.deleteFirmProfile(profileId);
        return firmResponse.isPresent() ? new ResponseEntity<>(firmResponse.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "get/{profileid}")
    public ResponseEntity<FirmResponse> retrieveFirmProfileById(@PathVariable(value = "profileid") Long profileId) {
        Optional<FirmResponse> firmResponse = profileService.retrieveFirmProfileById(profileId);
        return firmResponse.isPresent() ? new ResponseEntity<>(firmResponse.get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="/search")
    public ResponseEntity<List<PersonalResponse>> searchFirmProfiles(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(profileService.searchProfiles(searchString), HttpStatus.OK);
    }

}
