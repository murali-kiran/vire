package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ExperienceCommentRequest;
import com.vire.model.response.ExperienceCommentResponse;
import com.vire.service.ExperienceCommentService;
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
@RequestMapping(VireConstants.EXPERIENCECOMMENT_REQUEST_PATH_API)
public class ExperienceCommentController {

  @Autowired
  ExperienceCommentService experienceCommentService;

  @Operation(summary = "Create experience comment")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Experience comment created successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Experience comment creation failed",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<ExperienceCommentResponse> create(@Valid @RequestBody ExperienceCommentRequest request) {
    return new ResponseEntity<>(experienceCommentService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update experience comment")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "updated experience comment successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "experience comment updation failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<ExperienceCommentResponse> update(@Valid @RequestBody ExperienceCommentRequest request) {
    return new ResponseEntity<>(experienceCommentService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Delete experience comment")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Deletion of experience comment successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Deletion of experience comment failed",
                  content = @Content) })
  @DeleteMapping("/{experienceCommentId}")
  public ResponseEntity<ExperienceCommentResponse> delete(
          @PathVariable(value = "experienceCommentId") Long experienceCommentId) {
    return new ResponseEntity<>(experienceCommentService.delete(experienceCommentId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve all experience comments")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "retrieve of all experience comments successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "retrieve of all experience comments failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<ExperienceCommentResponse>> retrieveAll() {
    return new ResponseEntity<>(experienceCommentService.getAll(), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve of experience comment by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve of experience comment by ID successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve of experience comment by ID is failed",
                  content = @Content) })
  @GetMapping("/{experienceCommentId}")
  public ResponseEntity<ExperienceCommentResponse> retrieveById(@PathVariable Long experienceCommentId) {
    return new ResponseEntity<ExperienceCommentResponse>(experienceCommentService.retrieveById(experienceCommentId), HttpStatus.OK);
  }

  @Operation(summary = "Search experience comments")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Searching of experience comment successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Searching of experience comments failed",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<ExperienceCommentResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(experienceCommentService.search(searchString), HttpStatus.OK);
  }
}
