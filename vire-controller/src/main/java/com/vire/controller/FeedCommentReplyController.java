package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedCommentReplyRequest;
import com.vire.model.response.FeedCommentReplyResponse;
import com.vire.service.FeedCommentReplyService;
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
@RequestMapping(VireConstants.FEEDCOMMENTREPLY_REQUEST_PATH_API)
public class FeedCommentReplyController {

  @Autowired
  FeedCommentReplyService feedCommentReplyService;

  @Operation(summary = "Create Feed comment reply")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Create Feed comment reply Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Create Feed comment reply Failed",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<FeedCommentReplyResponse> create(@Valid @RequestBody FeedCommentReplyRequest request) {
    return new ResponseEntity<>(feedCommentReplyService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update Feed comment reply")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Update Feed comment reply Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Update Feed comment reply Failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<FeedCommentReplyResponse> update(@Valid @RequestBody FeedCommentReplyRequest request) {
    return new ResponseEntity<>(feedCommentReplyService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Delete Feed comment reply")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Delete Feed comment reply Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Delete Feed comment reply Failed",
                  content = @Content) })
  @DeleteMapping("/{feedCommentReplyId}")
  public ResponseEntity<FeedCommentReplyResponse> delete(
          @PathVariable(value = "id") Long feedCommentReplyId) {
    return new ResponseEntity<>(feedCommentReplyService.delete(feedCommentReplyId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve All Feed comment reply")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve All Feed comment reply Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve All Feed comment reply Failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<FeedCommentReplyResponse>> retrieveAll() {
    return new ResponseEntity<>(feedCommentReplyService.getAll(), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve Feed comment reply by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve Feed comment reply by ID Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve Feed comment reply by ID Failed",
                  content = @Content) })
  @GetMapping("/{feedCommentReplyId}")
  public ResponseEntity<FeedCommentReplyResponse> retrieveById(@PathVariable Long feedCommentReplyId) {
    return new ResponseEntity<FeedCommentReplyResponse>(feedCommentReplyService.retrieveById(feedCommentReplyId), HttpStatus.OK);
  }

  @Operation(summary = "Search Feed comment reply")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Search Feed comment reply Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedCommentReplyResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Search Feed comment reply Failed",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<FeedCommentReplyResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(feedCommentReplyService.search(searchString), HttpStatus.OK);
  }
}
