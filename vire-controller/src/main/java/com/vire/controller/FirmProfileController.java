package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.globalexception.ErrorInfo;
import com.vire.model.request.FirmRequest;
import com.vire.model.response.FirmResponse;
import com.vire.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(VireConstants.FIRM_PROFILE_REQUEST_PATH_API)
@Slf4j
public class FirmProfileController {

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
            @ApiResponse(responseCode = "201", description = "Created firm profile successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FirmResponse.class)) }),
            @ApiResponse(responseCode = "302", description = "profile with same Email or Mobile number already exists",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorInfo.class)) }),
            @ApiResponse(responseCode = "500", description = "Invalid supplied input",
                    content = @Content) })
    @PostMapping(value = "/create")
    public ResponseEntity<FirmResponse> createFirmProfile(@Valid @RequestBody FirmRequest request) {
        log.info("*********createFirmProfile***************: "+request);
        request.validate();
        FirmResponse firmResponse = profileService.createFirmProfile(request);
        setProfileCounts(Long.valueOf(firmResponse.getProfileId()),firmResponse);
        return new ResponseEntity<>(firmResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Update personal profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated firm profile successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FirmResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Invalid supplied input",
                    content = @Content) })
    @PutMapping(value = "/update")
    public ResponseEntity<FirmResponse> updateFirmProfile(@Valid @RequestBody FirmRequest request) {
        log.info("*********updateFirmProfile***************: "+request);
        FirmResponse firmResponse = profileService.updateFirmProfile(request);
        setProfileCounts(Long.valueOf(firmResponse.getProfileId()),firmResponse);
        return new ResponseEntity<>(firmResponse, HttpStatus.OK);
    }

    @Operation(summary = "Delete firm profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted firm profile successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FirmResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Firm profile with this ID not exist",
                    content = @Content) })
    @DeleteMapping(value = "delete/{profileid}")
    public ResponseEntity<FirmResponse> deleteFirmProfile(@PathVariable(value = "profileid") Long profileId) {
        Optional<FirmResponse> firmResponse = profileService.deleteFirmProfile(profileId);
        return firmResponse.isPresent() ? new ResponseEntity<>(firmResponse.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get a firm profile by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully got a firm profile by its id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FirmResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Firm profile with this ID not exist",
                    content = @Content) })
    @GetMapping(value = "get/{profileid}/{loginprofileid}")
    public ResponseEntity retrieveFirmProfileById(@PathVariable(value = "profileid") Long profileId, @PathVariable(value = "loginprofileid") Long loginProfileId) {
        Optional<FirmResponse> firmResponse = profileService.retrieveFirmProfileByIdAndLoginId(profileId, loginProfileId);
        //return firmResponse.isPresent() ? new ResponseEntity<>(firmResponse.get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NO_CONTENT);
        return firmResponse
                .stream()
                .map(profile -> {

                    setProfileCounts(profileId,profile);
                    var profileFollower = profileFollowersService
                            .search("profileId:"+profileId+" AND followerId:"+loginProfileId);
                    profile.setProfileFollowersResponse((profileFollower != null && !profileFollower.isEmpty()) ? profileFollower.get(0) : null);
                    return new ResponseEntity<>(profile, HttpStatus.OK);
                })
                .findFirst()
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

   /* @GetMapping(value="/search")
    public ResponseEntity<List<PersonalResponse>> searchFirmProfiles(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(profileService.searchProfiles(searchString), HttpStatus.OK);
    }*/

    private void setProfileCounts(final Long profileId, FirmResponse response){

        response.setThumbsDownCount(profileThumbsDownService.getThumbsDownCountOfProfile(profileId));
        response.setThumbsUpCount(profileThumbsUpService.getThumbsUpCountOfProfile(profileId));
        response.setFriendsCount(profileFollowersService.getFriendCountOfProfile(profileId));
        response.setStarsCount(experienceLikesService.getLikesCountOfProfile(profileId));

    }

}
