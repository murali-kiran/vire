package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.globalexception.ErrorInfo;
import com.vire.model.request.PersonalRequest;
import com.vire.model.response.PersonalResponse;
import com.vire.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Autowired
    ProfileThumbsDownService profileThumbsDownService;

    @Autowired
    ProfileThumbsUpService profileThumbsUpService;

    @Autowired
    ProfileFollowersService profileFollowersService;

    @Autowired
    ExperienceLikesService experienceLikesService;
    @Operation(summary = "Create personal profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created personal profile successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonalResponse.class)) }),
            @ApiResponse(responseCode = "302", description = "profile with same Email or Mobile number already exists",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorInfo.class)) }),
            @ApiResponse(responseCode = "500", description = "Invalid supplied input",
                    content = @Content) })
    @PostMapping(value = "/create")
    public ResponseEntity<PersonalResponse> createPersonalProfile(@Valid @RequestBody PersonalRequest request) {
        return new ResponseEntity<>(profileService.createPersonalProfile(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update personal profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated personal profile successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonalResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Invalid supplied input",
                    content = @Content) })
    @PutMapping(value = "/update")
    public ResponseEntity<PersonalResponse> updatePersonalProfile(@Valid @RequestBody PersonalRequest request) {
        return new ResponseEntity<>(profileService.updatePersonalProfile(request), HttpStatus.OK);
    }

    @Operation(summary = "Delete personal profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted personal profile successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonalResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Personal profile with this ID not exist",
                    content = @Content) })
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

    @Operation(summary = "Get a personal profile by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully got a personal profile by its id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonalResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Personal profile with this ID not exist",
                    content = @Content) })
    @GetMapping(value = "get/{profileid}")
    public ResponseEntity retrievePersonalProfileById(@PathVariable(value = "profileid") Long profileId) {
        Optional<PersonalResponse> profileResponse = profileService.retrievePersonalProfileById(profileId);
        return profileResponse
                .stream()
                .map(profile -> {
                    var thumbsDownCount = profileThumbsDownService.getThumbsDownCountOfProfile(profileId);
                    var thumbsUpCount = profileThumbsUpService.getThumbsUpCountOfProfile(profileId);
                    var friendsCount = profileFollowersService.getFriendCountOfProfile(profileId);
                    var starsCount = experienceLikesService.getLikesCountOfProfile(profileId);

                    profile.setThumbsDownCount(thumbsDownCount);
                    profile.setThumbsUpCount(thumbsUpCount);
                    profile.setFriendsCount(friendsCount);
                    profile.setStarsCount(starsCount);

                    return new ResponseEntity<>(profile, HttpStatus.OK);
                })
                .findFirst()
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PersonalResponse>> searchPersonalProfiles(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(profileService.searchProfiles(searchString), HttpStatus.OK);
    }
}
