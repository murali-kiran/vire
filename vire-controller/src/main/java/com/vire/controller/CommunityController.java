package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.CommunityRequest;
import com.vire.model.response.CommunityResponse;
import com.vire.model.response.KeyValueListResponse;
import com.vire.model.response.MinimalProfileResponse;
import com.vire.service.CommunityService;
import com.vire.constant.VireConstants;
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
import com.vire.model.response.PageWiseSearchResponse;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@CrossOrigin
@RequestMapping(VireConstants.COMMUNITY_REQUEST_PATH_API)
public class CommunityController {

  @Autowired
  CommunityService communityService;

  @Operation(summary = "Create Community")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "community created successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "community creation failed",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<CommunityResponse> create(@Valid @RequestBody CommunityRequest request) {
    return new ResponseEntity<>(communityService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update community")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "updated community successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "community updation failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<CommunityResponse> update(@Valid @RequestBody CommunityRequest request) {
    return new ResponseEntity<>(communityService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Deletion of community")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "deletion of community successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "community deletion failed",
                  content = @Content) })
  @DeleteMapping("/{communityId}")
  public ResponseEntity<CommunityResponse> delete(@PathVariable Long communityId) {
    return new ResponseEntity<>(communityService.delete(communityId), HttpStatus.OK);
  }

  @Operation(summary = "Get all communities")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all communities",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "communities retrieval failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<CommunityResponse>> retrieveAll() {
    return new ResponseEntity<>(communityService.getAll(), HttpStatus.OK);
  }
  
  @Operation(summary = "Get all communities PageWise")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all communities",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "communities retrieval failed",
                  content = @Content) })
  @GetMapping("/allPageWise")
  public ResponseEntity<PageWiseSearchResponse<CommunityResponse>> retrieveAllPageWise(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
                                                                       @RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize) {

    return new ResponseEntity<>(communityService.getAllPaged(pageNumber,pageSize), HttpStatus.OK);
  }
  
  
  @Operation(summary = "Get all communities with logged in profile id status")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all communities",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "communities retrieval failed",
                  content = @Content) })
  @GetMapping("loginProfileId/all/{profileId}")
  public ResponseEntity<List<CommunityResponse>> retrieveAll(@PathVariable String profileId) {
    return new ResponseEntity<>(communityService.getAll(profileId), HttpStatus.OK);
  }


  @Operation(summary = "Retrieve community by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieved community by ID successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "community retrieval failed",
                  content = @Content) })
  @GetMapping("/{communityId}")
  public ResponseEntity<CommunityResponse> retrieveById(@PathVariable Long communityId) {
    return new ResponseEntity<CommunityResponse>(communityService.retrieveById(communityId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve participants not of community by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve participants not of community by ID, success",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MinimalProfileResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve participants not of community, failed",
                  content = @Content) })
  @GetMapping("/newParticipants/{communityId}")
  public ResponseEntity<List<MinimalProfileResponse>> retrieveParticipantsNotOfCommunity(@PathVariable Long communityId) {
    return new ResponseEntity<>(communityService.retrieveProfilesNotPartOfCommunity(communityId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve  communities joined by profile ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve  communities joined by profile ID successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve  communities joined by profile ID failed",
                  content = @Content) })
  @GetMapping("/joinedProfiles/{profileId}")
  public ResponseEntity<List<CommunityResponse>> retrieveJoinedByProfileId(@PathVariable Long profileId) {
    return new ResponseEntity<>(communityService.retrieveCommunitiesJoined(profileId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve  communities created and joined by profile ID statusList: comma separated statuses ex: Admin,Accepted,Blocked,Rejected,Requested")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve  communities created and joined by profile ID successfully" ,
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = KeyValueListResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve  communities created and joined by profile ID failed",
                  content = @Content) })
  @GetMapping("/getByProfileStatus/{profileId}/{statusList}")
  public ResponseEntity<List<KeyValueListResponse>> retrieveCommunitiesByProfileStatus(@PathVariable Long profileId, @PathVariable String statusList) {
    return new ResponseEntity<>(communityService.retrieveCommunitiesByProfileStatus(profileId, statusList), HttpStatus.OK);
  }

  @Operation(summary = "Searching of community")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "searching community successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "searching of community failed",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<CommunityResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(communityService.search(searchString), HttpStatus.OK);
  }
  
  @GetMapping(value = "/blockCommunity/{communityId}")
  public ResponseEntity<Boolean>  blockProfile(@PathVariable("communityId") String profileId,@RequestParam("isBlocked") Boolean isBlocked) {
    var blockedStatus = communityService.blockProfile(Long.valueOf(profileId),isBlocked);
    return new ResponseEntity<>(blockedStatus, HttpStatus.OK);
  }
  
}
