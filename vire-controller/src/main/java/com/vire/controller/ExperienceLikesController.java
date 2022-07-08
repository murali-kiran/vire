package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ExperienceLikesRequest;
import com.vire.model.response.ExperienceLikesResponse;
import com.vire.service.ExperienceLikesService;
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
@RequestMapping(VireConstants.EXPERIENCELIKES_REQUEST_PATH_API)
public class ExperienceLikesController {

  @Autowired
  ExperienceLikesService experienceLikesService;

  @Operation(summary = "Create Experience Likes")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Create Experience Likes Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Create Experience Likes Failed",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<ExperienceLikesResponse> create(@Valid @RequestBody ExperienceLikesRequest request) {
    return new ResponseEntity<>(experienceLikesService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update Experience Likes")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Update Experience Likes Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Update Experience Likes Failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<ExperienceLikesResponse> update(@Valid @RequestBody ExperienceLikesRequest request) {
    return new ResponseEntity<>(experienceLikesService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Delete Experience Likes")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Delete Experience Likes Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Delete Experience Likes Failed",
                  content = @Content) })
  @DeleteMapping("/{experienceLikesId}")
  public ResponseEntity<ExperienceLikesResponse> delete(
          @PathVariable(value = "experienceLikesId") Long experienceLikesId) {
    return new ResponseEntity<>(experienceLikesService.delete(experienceLikesId), HttpStatus.OK);
  }

  @Operation(summary = "Delete Experience Likes by Experienceid, profileid")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Delete Experience Likes Successful by Experienceid, profileid",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Delete Experience Likes by Experienceid, profileid Failed ",
                  content = @Content) })
  @DeleteMapping("/deletebyexp/{experienceId}/{profileId}")
  public ResponseEntity<ExperienceLikesResponse> delete(
          @PathVariable(value = "experienceId") Long experienceId, @PathVariable(value = "profileId") Long profileId) {
    return new ResponseEntity<>(experienceLikesService.deleteByProfileAndExperience(experienceId, profileId), HttpStatus.OK);
  }
  @Operation(summary = "Retrieve All Experience Likes")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve All Experience Likes Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve All Experience Likes Failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<ExperienceLikesResponse>> retrieveAll() {
    return new ResponseEntity<>(experienceLikesService.getAll(), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve Experience Likes by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve Experience Likes by ID Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve Experience Likes by ID Failed",
                  content = @Content) })
  @GetMapping("/{experienceLikesId}")
  public ResponseEntity<ExperienceLikesResponse> retrieveById(@PathVariable Long experienceLikesId) {
    return new ResponseEntity<ExperienceLikesResponse>(experienceLikesService.retrieveById(experienceLikesId), HttpStatus.OK);
  }

  @Operation(summary = "Search Experience Likes")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Search Experience Likes Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Sreach Experience Likes Failed",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<ExperienceLikesResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(experienceLikesService.search(searchString), HttpStatus.OK);
  }
}
