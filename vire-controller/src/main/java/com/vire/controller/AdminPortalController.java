package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.response.*;
import com.vire.model.response.view.FeedsViewResponse;
import com.vire.model.response.view.SocialViewResponse;
import com.vire.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = VireConstants.ADMIN_PORTAL_API)
@CrossOrigin
public class AdminPortalController {

  @Autowired AdminPortalService adminPortalService;

  @Autowired
  ProfileService profileService;

  @Autowired
  CommunityService communityService;

  @Autowired
  ChannelService channelService;


  @GetMapping(value = "/homepage")
  public AdminHomeResponse getHomePage() {
    return adminPortalService.getHomePageDetails();
  }

  @GetMapping(value = "/users")
  public PageWiseSearchResponse<ProfileResponse> getUsers(
          @RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
          @RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize
  ) {
    return adminPortalService.getAllUsers(pageNumber, pageSize);
  }


  @GetMapping(value = "/blockerUsers")
  public PageWiseSearchResponse<ProfileResponse> getBlockedUsers(
          @RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
          @RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize
  ) {
    return adminPortalService.getAllBlockedUsers(pageNumber, pageSize);
  }

  @GetMapping(value = "/deleteProfile/{profileId}")
  public ResponseEntity<ProfileResponse> deleteProfile(@PathVariable("profileId") String profileId) {
    var minimalProfile = adminPortalService.deleteProfile(Long.valueOf(profileId));
    return minimalProfile.isPresent() ? new ResponseEntity<>(minimalProfile.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping(value = "/blockProfile/{profileId}")
  public ResponseEntity<Boolean>  blockProfile(@PathVariable("profileId") String profileId,@RequestParam("isBlocked") Boolean isBlocked) {
     var blockedStatus = adminPortalService.blockProfile(Long.valueOf(profileId),isBlocked);
     return new ResponseEntity<>(blockedStatus, HttpStatus.OK);
  }
  
  @GetMapping(value = "/feeds")
  public List<FeedsViewResponse> getFeeds() {
    return adminPortalService.getAllFeeds();
  }

  @DeleteMapping(value = "/feed/{feedId}")
  public ResponseEntity<FeedsResponse> deleteFeed(@PathVariable("feedId") String feedId) {
    return new ResponseEntity<>(adminPortalService.deleteFeed(feedId), HttpStatus.OK);
  }

  @GetMapping(value = "/socials")
  public ResponseEntity<PageWiseSearchResponse<SocialPostResponse>>  getSocials(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
                                                                                @RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize) {
    return new ResponseEntity<>(adminPortalService.getAllSocialsPaged(pageNumber,pageSize), HttpStatus.OK);
  }

  @DeleteMapping(value = "/social/{socialId}")
  public ResponseEntity<SocialResponse> deleteSocial(@PathVariable("socialId") String socialId) {
    return new ResponseEntity<>(adminPortalService.deleteSocial(socialId), HttpStatus.OK);
  }

  @GetMapping(value = "/experiences")
  public ResponseEntity<PageWiseSearchResponse<ExperienceDetailResponse>>  getExperiences(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
                                                                                @RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize) {
    return new ResponseEntity<>(adminPortalService.getAllExperiencesPaged(pageNumber,pageSize), HttpStatus.OK);
  }

  @DeleteMapping(value = "/experience/{experienceId}")
  public ResponseEntity<ExperienceResponse> deleteExperience(@PathVariable("experienceId") String experienceId) {
    return new ResponseEntity<>(adminPortalService.deleteExperience(experienceId), HttpStatus.OK);
  }

  @Operation(summary = "Search Profile")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Search Profile Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ProfileResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Search Profile Failed",
                  content = @Content) })
  @GetMapping("profile/searchPageWise")
  public ResponseEntity<PageWiseSearchResponse<ProfileResponse>> searchProfilesPageWise(@RequestParam(value = "search") String searchString,
                                                                                        @RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
                                                                                        @RequestParam(value = "size", defaultValue = "3", required = false) Integer pageSize) {
    return new ResponseEntity<>(profileService.searchAnyProfilesPaged(searchString,pageNumber,pageSize), HttpStatus.OK);
  }

  @Operation(summary = "Get all communities PageWise")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all communities",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "communities retrieval failed",
                  content = @Content) })
  @GetMapping("community/allPageWise")
  public ResponseEntity<PageWiseSearchResponse<CommunityResponse>> retrieveAllCommunityPageWise(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
                                                                                       @RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize) {

    return new ResponseEntity<>(communityService.getAllPaged(pageNumber,pageSize), HttpStatus.OK);
  }

  @GetMapping(value = "community/blockCommunity/{communityId}")
  public ResponseEntity<Boolean>  blockCommunity(@PathVariable("communityId") String profileId,@RequestParam("isBlocked") Boolean isBlocked) {
    var blockedStatus = communityService.blockProfile(Long.valueOf(profileId),isBlocked);
    return new ResponseEntity<>(blockedStatus, HttpStatus.OK);
  }

  @Operation(summary = "Deletion of community")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "deletion of community successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "community deletion failed",
                  content = @Content) })
  @DeleteMapping("community/{communityId}")
  public ResponseEntity<CommunityResponse> deleteCommunity(@PathVariable Long communityId) {
    return new ResponseEntity<>(communityService.delete(communityId), HttpStatus.OK);
  }


  @Operation(summary = "Get all channels PageWise")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all channels",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "channels retrieval failed",
                  content = @Content) })
  @GetMapping("channel/allPageWise")
  public ResponseEntity<PageWiseSearchResponse<ChannelResponse>> retrieveAllChannelPageWise(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
                                                                                     @RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize) {

    return new ResponseEntity<>(channelService.getAllPaged(pageNumber,pageSize), HttpStatus.OK);
  }

  @Operation(summary = "Delete channel")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully deleted channel",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "delete channel failed",
                  content = @Content) })
  @DeleteMapping("channel/{channelId}")
  public ResponseEntity<ChannelResponse> deleteChannel(@PathVariable(value = "channelId") Long channelId) {
    return new ResponseEntity<>(channelService.delete(channelId), HttpStatus.OK);
  }


}
