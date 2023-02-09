package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.AdminMessageRequest;
import com.vire.model.request.AppRestrictionRequest;
import com.vire.model.request.LoginRequest;
import com.vire.model.request.MasterRequest;
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
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

  @Autowired
  MasterService masterService;

  @Autowired
  AppRestrictionService appRestrictionService;

  @Autowired
  AdminMessageService adminMessageService;


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

  @Operation(summary = "Retrieve All Paged Master Grouby Title")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve All Paged Master Successful",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = PagedResponse.class))}),
          @ApiResponse(responseCode = "500", description = "Retrieve All Paged Master Failed",
                  content = @Content)})
  @GetMapping("/master/all")
  public ResponseEntity<Map<String,List<MasterResponse>>> retrieveMasterGroupByTitle() {
    return new ResponseEntity<>(masterService.getAllGroupByTitle(), HttpStatus.OK);
  }

  @Operation(summary = "Create Master ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Create Master Successful",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class))}),
          @ApiResponse(responseCode = "500", description = "Create Master Failed",
                  content = @Content)})
  @PostMapping("/master/create")
  public ResponseEntity<MasterResponse> create(@Valid @RequestBody MasterRequest request) {
    return new ResponseEntity<>(masterService.create(request), HttpStatus.CREATED);
  }


  @Operation(summary = "Update Master ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Update Master Successful",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class))}),
          @ApiResponse(responseCode = "500", description = "Update Master Failed",
                  content = @Content)})
  @PutMapping("/master/update")
  public ResponseEntity<MasterResponse> update(@Valid @RequestBody MasterRequest request) {
    return new ResponseEntity<>(masterService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Delete Master ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Delete Master Successful",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class))}),
          @ApiResponse(responseCode = "500", description = "Delete Master Failed",
                  content = @Content)})
  @DeleteMapping("/master/delete/{masterId}")
  public ResponseEntity<MasterResponse> delete(
          @PathVariable(value = "masterId") Long masterId) {
    return new ResponseEntity<>(masterService.delete(masterId), HttpStatus.OK);
  }


  @Operation(summary = " Update App Restrictions limit ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = " Update App Restrictions limit Success",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class))}),
          @ApiResponse(responseCode = "500", description = " Update App Restrictions limit  Failed",
                  content = @Content)})
  @PutMapping("/appRestriction/update")
  public ResponseEntity<AppRestrictionResponse> updateRestrictionResponse(@RequestBody AppRestrictionRequest request) {
    return new ResponseEntity<>(appRestrictionService.update(request), HttpStatus.CREATED);
  }


  @Operation(summary = " Get App Restrictions limit ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = " Update App Restrictions limit Success",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class))}),
          @ApiResponse(responseCode = "500", description = " Update App Restrictions limit  Failed",
                  content = @Content)})
  @GetMapping("/appRestriction/get")
  public ResponseEntity<AppRestrictionResponse> getAppRestriction() {
    return new ResponseEntity<>(appRestrictionService.getAll().get(0), HttpStatus.OK);
  }

  @Operation(summary = " Admin portal login ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = " Admin portal login Success",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class))}),
          @ApiResponse(responseCode = "500", description = " Admin portal login  Failed",
                  content = @Content)})
  @PostMapping("/adminPortalLogin")
  public ResponseEntity<AdminPortalLoginResponse> AdminPortalLogin(@Valid @RequestBody LoginRequest request) {

    var adminLoginResponse =  new AdminPortalLoginResponse();
    final long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

    if("vireadmin@vire.com".equals(request.getEmailOrphonenumber()) && "virepassword".equals(new String(Base64Utils.decode(request.getPassword().getBytes())))){
      adminLoginResponse.setEmail(request.getEmailOrphonenumber());
      adminLoginResponse.setExpiresIn(new Date().getTime()+MILLISECONDS_PER_DAY);
      adminLoginResponse.setStatus("success");
      return new ResponseEntity<>(adminLoginResponse, HttpStatus.OK);
    }else{
      adminLoginResponse.setStatus("failure");
      return new ResponseEntity<>(adminLoginResponse, HttpStatus.UNAUTHORIZED);
    }

  }

  @PostMapping("/adminMessage/create")
  public ResponseEntity<AdminMessageResponse> createAdminMessage(@RequestBody AdminMessageRequest request) {
    return new ResponseEntity<>(adminMessageService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/adminMessage/update")
  public ResponseEntity<AdminMessageResponse> updateAdminMessage(@RequestBody AdminMessageRequest request) {
    return new ResponseEntity<>(adminMessageService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/adminMessage/delete/{adminMessageId}")
  public ResponseEntity<AdminMessageResponse> deleteAdminMessage(
          @PathVariable(value = "adminMessageId") Long adminMessageId) {
    return new ResponseEntity<>(adminMessageService.delete(adminMessageId), HttpStatus.OK);
  }

  @GetMapping("/adminMessage/all")
  public ResponseEntity<List<AdminMessageResponse>> retrieveAllAdminMessage() {
    return new ResponseEntity<>(adminMessageService.getAll(), HttpStatus.OK);
  }

  @Operation(summary = "Get all adminMessages PageWise")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all adminMessages",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "adminMessages retrieval failed",
                  content = @Content) })
  @GetMapping("/adminMessage/allPageWise")
  public ResponseEntity<PageWiseSearchResponse<AdminMessageResponse>> retrieveAllAdminMessagePageWise(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
                                                                                            @RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize) {

    return new ResponseEntity<>(adminMessageService.getAllPaged(pageNumber,pageSize), HttpStatus.OK);
  }


}
