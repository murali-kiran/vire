package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedsRequest;
import com.vire.model.response.FeedsFullResponse;
import com.vire.model.response.FeedsResponse;
import com.vire.service.FeedsService;
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
@RequestMapping(VireConstants.FEEDS_REQUEST_PATH_API)
public class FeedsController {
    @Autowired
    FeedsService feedsService;

    @Operation(summary = "Create Feeds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create Feeds Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Create Feeds Failed",
                    content = @Content) })
    @PostMapping("/create")
    public ResponseEntity<FeedsResponse> create(@Valid @RequestBody FeedsRequest request) {
        return new ResponseEntity<>(feedsService.createFeeds(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Feeds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update Feeds Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Update Feeds Failed",
                    content = @Content) })
    @PutMapping("/update")
    public ResponseEntity<FeedsResponse> update(@Valid @RequestBody FeedsRequest request) {
        return new ResponseEntity<>(feedsService.updateFeeds(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Feeds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Feeds Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Delete Feeds Failed",
                    content = @Content) })
    @DeleteMapping("/{feedid}")
    public ResponseEntity<FeedsResponse> delete(
            @PathVariable(value = "feedid") Long feedsId) {
        return new ResponseEntity<>(feedsService.deleteFeedsPost(feedsId), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve All Feeds ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve All Feeds Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Retrieve All Feeds Failed",
                    content = @Content) })
    @GetMapping("/all")
    public ResponseEntity<List<FeedsResponse>> retrieveAll() {
        return new ResponseEntity<>(feedsService.getFeeds(), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve Feeds by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve Feeds by ID Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Retrieve Feeds by ID Failed",
                    content = @Content) })
    @GetMapping("/{feedid}")
    public ResponseEntity<FeedsResponse> retrieveById(@PathVariable(name = "feedid") Long feedsId) {
        return new ResponseEntity<FeedsResponse>(feedsService.retrieveById(feedsId), HttpStatus.OK);
    }

    @Operation(summary = "Search Feeds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Feeds Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Search Feeds Failed",
                    content = @Content) })
    @GetMapping("search")
    public ResponseEntity<List<FeedsResponse>> search(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(feedsService.search(searchString), HttpStatus.OK);
    }

    @Operation(summary = "Find Feeds by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Feeds by ID Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsFullResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Find Feeds by ID Failed",
                    content = @Content) })
    @GetMapping("feedsdetails/{feedid}")
    public ResponseEntity<FeedsFullResponse> findById(@PathVariable(name = "feedsid") Long feedsPostId) {
        return new ResponseEntity<FeedsFullResponse>(feedsService.retrieveFeedsDetailsById(feedsPostId), HttpStatus.OK);
    }

    @Operation(summary = "Find Feeds By ProfileId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Feeds By ProfileId Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsFullResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Find Feeds By ProfileId Failed",
                    content = @Content) })
    @GetMapping("feedsposts/{profileid}")
    public ResponseEntity<List<FeedsFullResponse>> findByIdProfileId(@PathVariable(name = "profileid") Long profileId) {
        return new ResponseEntity<List<FeedsFullResponse>>(feedsService.retrievePostsByProfileId(profileId), HttpStatus.OK);
    }
}
