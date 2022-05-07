package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.PersonalRequest;
import com.vire.model.response.PersonalResponse;
import com.vire.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(VireConstants.PERSONAL_PROFILE_REQUEST_PATH_API)
public class PersonalProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping(value = "/create")
    public ResponseEntity<PersonalResponse> createPersonalProfile(@Valid @RequestBody PersonalRequest request) {
        return new ResponseEntity<>(profileService.createPersonalProfile(request), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<PersonalResponse> updatePersonalProfile(@Valid @RequestBody PersonalRequest request) {
        return new ResponseEntity<>(profileService.updatePersonalProfile(request), HttpStatus.OK);
    }


    @DeleteMapping(value = "delete/{profileid}")
    public ResponseEntity<PersonalResponse> deletePersonalProfile(
            @PathVariable(value = "profileid") Long profileId) {
        Optional<PersonalResponse> profileResponse = profileService.deletePersonalProfile(profileId);
        return profileResponse.isPresent() ? new ResponseEntity<>(profileResponse.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*@GetMapping
    public ResponseEntity<List<PersonalResponse>> retrieveAllProfiles() {
        return new ResponseEntity<>(profileService.retrieveAllProfiles(), HttpStatus.OK);
    }*/

    @GetMapping(value = "get/{profileid}")
    public ResponseEntity<PersonalResponse> retrievePersonalProfileById(@PathVariable(value = "profileid") Long profileId) {
        Optional<PersonalResponse> profileResponse = profileService.retrievePersonalProfileById(profileId);
        return profileResponse
                .stream()
                .map(profile -> new ResponseEntity<>(profile, HttpStatus.OK))
                .findFirst()
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PersonalResponse>> searchPersonalProfiles(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(profileService.searchProfiles(searchString), HttpStatus.OK);
    }
}
