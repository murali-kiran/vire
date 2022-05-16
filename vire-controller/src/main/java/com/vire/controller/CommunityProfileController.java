package com.vire.controller;

import com.vire.model.request.CommunityProfileRequest;
import com.vire.model.response.CommunityProfileResponse;
import com.vire.service.CommunityProfileService;
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

import javax.validation.Valid;
import java.util.List;
@Validated
@RestController
@RequestMapping(VireConstants.COMMUNITYPROFILE_REQUEST_PATH_API)
public class CommunityProfileController {

  @Autowired
  CommunityProfileService communityProfileService;

  @Operation(summary = "Create Community profile")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "community profile created successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityProfileResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "community profile creation failed",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<CommunityProfileResponse> create(@Valid @RequestBody CommunityProfileRequest request) {
    return new ResponseEntity<>(communityProfileService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update community profile")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "updated community profile successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityProfileResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "community profile updation failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<CommunityProfileResponse> update(@Valid @RequestBody CommunityProfileRequest request) {
    return new ResponseEntity<>(communityProfileService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Deletion of community profile")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "deletion of community profile successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityProfileResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "community profile deletion failed",
                  content = @Content) })
  @DeleteMapping("/{communityProfileId}")
  public ResponseEntity<CommunityProfileResponse> delete(
          @PathVariable(value = "id") Long communityProfileId) {
    return new ResponseEntity<>(communityProfileService.delete(communityProfileId), HttpStatus.OK);
  }

  @Operation(summary = "Get all communities profile")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all communities profiles",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityProfileResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "community profiles retreival failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<CommunityProfileResponse>> retrieveAll() {
    return new ResponseEntity<>(communityProfileService.getAll(), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve community profile by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieved community profile by ID sucessfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityProfileResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "community profile retrieval failed",
                  content = @Content) })
  @GetMapping("/{communityProfileId}")
  public ResponseEntity<CommunityProfileResponse> retrieveById(@PathVariable Long communityProfileId) {
    return new ResponseEntity<CommunityProfileResponse>(communityProfileService.retrieveById(communityProfileId), HttpStatus.OK);
  }

  @Operation(summary = "Searching of community profile ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "searching community profiles successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CommunityProfileResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "searching of community profiles failed",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<CommunityProfileResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(communityProfileService.search(searchString), HttpStatus.OK);
  }
}
