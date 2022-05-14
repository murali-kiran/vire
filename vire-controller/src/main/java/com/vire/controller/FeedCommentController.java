package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedCommentRequest;
import com.vire.model.response.FeedCommentResponse;
import com.vire.service.FeedCommentService;
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
@RequestMapping(VireConstants.FEEDCOMMENT_REQUEST_PATH_API)
public class FeedCommentController {

  @Autowired
  FeedCommentService feedCommentService;

  @Operation(summary = "Create Feed Comment")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Create Feed Comment Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Create Feed Comment Failed",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<FeedCommentResponse> create(@Valid @RequestBody FeedCommentRequest request) {
    return new ResponseEntity<>(feedCommentService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update Feed Comment")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Update Feed Comment Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Update Feed Comment Failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<FeedCommentResponse> update(@Valid @RequestBody FeedCommentRequest request) {
    return new ResponseEntity<>(feedCommentService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Delete Feed Comment")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Delete Feed Comment Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Delete Feed Comment Failed",
                  content = @Content) })
  @DeleteMapping("/{feedCommentId}")
  public ResponseEntity<FeedCommentResponse> delete(
          @PathVariable(value = "id") Long feedCommentId) {
    return new ResponseEntity<>(feedCommentService.delete(feedCommentId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve All Feed Comments")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve All Feed Comments Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve All Feed Comments Failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<FeedCommentResponse>> retrieveAll() {
    return new ResponseEntity<>(feedCommentService.getAll(), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve Feed Comment by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve Feed Comment by ID Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve Feed Comment by ID Failed",
                  content = @Content) })
  @GetMapping("/{feedCommentId}")
  public ResponseEntity<FeedCommentResponse> retrieveById(@PathVariable Long feedCommentId) {
    return new ResponseEntity<FeedCommentResponse>(feedCommentService.retrieveById(feedCommentId), HttpStatus.OK);
  }

  @Operation(summary = "Search Feed Comment")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Search Feed Comment Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Search Feed Comment Failed",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<FeedCommentResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(feedCommentService.search(searchString), HttpStatus.OK);
  }
}
