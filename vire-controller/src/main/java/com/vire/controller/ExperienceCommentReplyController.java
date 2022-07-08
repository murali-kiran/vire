package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ExperienceCommentReplyRequest;
import com.vire.model.response.ExperienceCommentReplyResponse;
import com.vire.service.ExperienceCommentReplyService;
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
@RequestMapping(VireConstants.EXPERIENCECOMMENTREPLY_REQUEST_PATH_API)
public class ExperienceCommentReplyController {

  @Autowired
  ExperienceCommentReplyService experienceCommentReplyService;

  @Operation(summary = "Create Experience Comment Reply")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "creation of Experience Comment Reply successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "creation of Experience Comment Reply successfully",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<ExperienceCommentReplyResponse> create(@Valid @RequestBody ExperienceCommentReplyRequest request) {
    return new ResponseEntity<>(experienceCommentReplyService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update Experience Comment Reply")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "updation of Experience Comment Reply successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "updation of Experience Comment Reply failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<ExperienceCommentReplyResponse> update(@Valid @RequestBody ExperienceCommentReplyRequest request) {
    return new ResponseEntity<>(experienceCommentReplyService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Delete Experience Comment Reply")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Deletion of Experience Comment Reply successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Deletion of Experience Comment Reply failed",
                  content = @Content) })
  @DeleteMapping("/{experienceCommentReplyId}")
  public ResponseEntity<ExperienceCommentReplyResponse> delete(
          @PathVariable(value = "experienceCommentReplyId") Long experienceCommentReplyId) {
    return new ResponseEntity<>(experienceCommentReplyService.delete(experienceCommentReplyId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve All Experience Comment Reply")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve All of Experience Comment Reply successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve All of Experience Comment Reply failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<ExperienceCommentReplyResponse>> retrieveAll() {
    return new ResponseEntity<>(experienceCommentReplyService.getAll(), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve Experience Comment Reply by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve Experience Comment Reply by ID successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve Experience Comment Reply by ID failed",
                  content = @Content) })
  @GetMapping("/{experienceCommentReplyId}")
  public ResponseEntity<ExperienceCommentReplyResponse> retrieveById(@PathVariable Long experienceCommentReplyId) {
    return new ResponseEntity<ExperienceCommentReplyResponse>(experienceCommentReplyService.retrieveById(experienceCommentReplyId), HttpStatus.OK);
  }

  @Operation(summary = "Search Experience Comment Reply")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Search Experience Comment Reply Successfull",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ExperienceCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Search Experience Comment Reply failed ",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<ExperienceCommentReplyResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(experienceCommentReplyService.search(searchString), HttpStatus.OK);
  }
}
