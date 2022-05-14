package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedLikesRequest;
import com.vire.model.response.FeedLikesResponse;
import com.vire.service.FeedLikesService;
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
@RequestMapping(VireConstants.FEEDLIKES_REQUEST_PATH_API)
public class FeedLikesController {

  @Autowired
  FeedLikesService feedLikesService;

  @Operation(summary = "Create Feed Likes")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Create Feed Likes Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Create Feed Likes Failed",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<FeedLikesResponse> create(@Valid @RequestBody FeedLikesRequest request) {
    return new ResponseEntity<>(feedLikesService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update Feed Likes")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Update Feed Likes Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Update Feed Likes Failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<FeedLikesResponse> update(@Valid @RequestBody FeedLikesRequest request) {
    return new ResponseEntity<>(feedLikesService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Delete Feed Likes")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Delete Feed Likes Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Delete Feed Likes Failed",
                  content = @Content) })
  @DeleteMapping("/{feedLikesId}")
  public ResponseEntity<FeedLikesResponse> delete(
          @PathVariable(value = "id") Long feedLikesId) {
    return new ResponseEntity<>(feedLikesService.delete(feedLikesId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve All Feed Likes")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve All Feed Likes Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve All Feed Likes Failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<FeedLikesResponse>> retrieveAll() {
    return new ResponseEntity<>(feedLikesService.getAll(), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve Feed Likes by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve Feed Likes by ID Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve Feed Likes by ID Failed",
                  content = @Content) })
  @GetMapping("/{feedLikesId}")
  public ResponseEntity<FeedLikesResponse> retrieveById(@PathVariable Long feedLikesId) {
    return new ResponseEntity<FeedLikesResponse>(feedLikesService.retrieveById(feedLikesId), HttpStatus.OK);
  }

  @Operation(summary = "Search Feed Likes")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Search Feed Likes Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = FeedLikesResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Search Feed Likes Failed",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<FeedLikesResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(feedLikesService.search(searchString), HttpStatus.OK);
  }
}
