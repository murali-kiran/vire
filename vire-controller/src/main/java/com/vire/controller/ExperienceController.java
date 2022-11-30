package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ExperienceFilterRequest;
import com.vire.model.request.ExperienceRequest;
import com.vire.model.response.ExperienceDetailResponse;
import com.vire.model.response.ExperienceResponse;
import com.vire.service.ExperienceService;
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
@RequestMapping(VireConstants.EXPERIENCE_REQUEST_PATH_API)
public class ExperienceController {

  @Autowired
  ExperienceService experienceService;

  @Operation(summary = "Create Experience")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Create Experience Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Experience Failed",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<ExperienceResponse> create(@Valid @RequestBody ExperienceRequest request) {
    return new ResponseEntity<>(experienceService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update Experience")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Experience Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Update Experience Failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<ExperienceResponse> update(@Valid @RequestBody ExperienceRequest request) {
    return new ResponseEntity<>(experienceService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Delete Experience")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Delete Experience Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Delete Experience Failed",
                  content = @Content) })
  @DeleteMapping("/{experienceId}")
  public ResponseEntity<ExperienceResponse> delete(
          @PathVariable(value = "experienceId") Long experienceId) {
    return new ResponseEntity<>(experienceService.updateDeletedTime(experienceId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve All Experience")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve All Experience Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve All Experience Failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<ExperienceResponse>> retrieveAll() {
    return new ResponseEntity<>(experienceService.getAll(), HttpStatus.OK);
  }
  @Operation(summary = "Retrieve All Experience by Profile")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve All Experience by Profile Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceDetailResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve All Experience by Profile Failed",
                  content = @Content) })
  @GetMapping("byprofile/{profileid}")
  public ResponseEntity<List<ExperienceDetailResponse>> retrieveAllByProfile(@PathVariable(name = "profileid") Long profileId) {
    return new ResponseEntity<>(experienceService.retrieveAllByProfile(profileId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve All Experience by Filter")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve All Experience by Filter Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceDetailResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve All Experience by Filter Failed",
                  content = @Content) })
  @PostMapping("/filterexperience")
  public ResponseEntity<List<ExperienceDetailResponse>> retrieveAllByProfile(@Valid @RequestBody ExperienceFilterRequest request) {
    return new ResponseEntity<>(experienceService.retrieveAllByFilter(request), HttpStatus.OK);
  }
  @Operation(summary = "Retrieve Experience by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve Experience by ID Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve Experience by ID Experience Failed",
                  content = @Content) })
  @GetMapping("/{experienceId}")
  public ResponseEntity<ExperienceResponse> retrieveById(@PathVariable Long experienceId) {
    return new ResponseEntity<ExperienceResponse>(experienceService.retrieveById(experienceId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve Detailed Experience by experience ID, profile Id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve Detailed Experience by experience ID, profile Id Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceDetailResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve Detailed Experience by experience ID, profile Id Experience Failed",
                  content = @Content) })
  @GetMapping("details/{experienceId}/{profileId}")
  public ResponseEntity<ExperienceDetailResponse> retrieveDetailsById(@PathVariable Long experienceId, @PathVariable Long profileId) {
    return new ResponseEntity<ExperienceDetailResponse>(experienceService.retrieveExperienceDetailsById(experienceId, profileId), HttpStatus.OK);
  }
  @Operation(summary = "Search Experience")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Search Experience Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Search Experience Failed",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<ExperienceResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(experienceService.search(searchString), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve Experiences created by Profile")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve Experiences created by Profile Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceDetailResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve Experiences created by Profile Failed",
                  content = @Content) })
  @GetMapping("createdexperiences/{profileid}")
  public ResponseEntity<List<ExperienceDetailResponse>> retrieveAllCreatedByProfile(@PathVariable(name = "profileid") String profileId) {
    return new ResponseEntity<>(experienceService.retrieveAllCreatedByProfile(profileId), HttpStatus.OK);
  }

}
