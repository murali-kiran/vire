package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedsSendToRequest;
import com.vire.model.response.FeedsSendToResponse;
import com.vire.service.FeedsSendToService;
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
@RequestMapping(VireConstants.SEND_TO_REQUEST_PATH_API)
public class FeedsSendToController {

    @Autowired
    FeedsSendToService feedsSendToService;

    @Operation(summary = "Create Feeds Send")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create Feeds Send Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsSendToResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Create Feeds Send Failed",
                    content = @Content) })
    @PostMapping("/create")
    public ResponseEntity<FeedsSendToResponse> createSent(@Valid @RequestBody FeedsSendToRequest request){
        return new ResponseEntity<>(feedsSendToService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Feeds Send")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update Feeds Send Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsSendToResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Update Feeds Send Failed",
                    content = @Content) })
    @PutMapping("/update")
    public ResponseEntity<FeedsSendToResponse> updateSent(@Valid @RequestBody FeedsSendToRequest request){
        return new ResponseEntity<>(feedsSendToService.update(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Feeds Send")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Feeds Send Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsSendToResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Delete Feeds Send Failed",
                    content = @Content) })
    @DeleteMapping("/{feedsendtoid}")
    public ResponseEntity<FeedsSendToResponse> delete(
            @PathVariable(value = "feedsendtoid") Long sentId) {
        return new ResponseEntity<>(feedsSendToService.deletePostSent(sentId), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve Feeds Send by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve Feeds Send by ID Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsSendToResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Retrieve Feeds Send by ID Failed",
                    content = @Content) })
    @GetMapping("/{feedsendtoid}")
    public ResponseEntity<FeedsSendToResponse> retrieveById(@PathVariable(name="feedsendtoid") Long postSentId){
        return new ResponseEntity<FeedsSendToResponse>(feedsSendToService.retrieveById(postSentId), HttpStatus.OK);
    }

    @Operation(summary = "Search Feeds Send")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Feeds Send Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedsSendToResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Search Feeds Send Failed",
                    content = @Content) })
    @GetMapping("search")
    public ResponseEntity<List<FeedsSendToResponse>> searchSent(
            @RequestParam(value = "searchpostsent") String searchString) {
        return new ResponseEntity<>(feedsSendToService.searchSent(searchString), HttpStatus.OK);
    }
}
