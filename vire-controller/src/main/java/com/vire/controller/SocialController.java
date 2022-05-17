package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialRequest;
import com.vire.model.response.SocialPostResponse;
import com.vire.model.response.SocialResponse;
import com.vire.service.SocialService;
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
@RequestMapping(VireConstants.SOCIAL_REQUEST_PATH_API)
public class SocialController {
    @Autowired
    SocialService socialService;

    @Operation(summary = "Create Social")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create Social Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Create Social Failed",
                    content = @Content)})
    @PostMapping("/create")
    public ResponseEntity<SocialResponse> create(@Valid @RequestBody SocialRequest request) {
        return new ResponseEntity<>(socialService.createSocial(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Social")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update Social Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Update Social Failed",
                    content = @Content)})
    @PutMapping("/update")
    public ResponseEntity<SocialResponse> update(@Valid @RequestBody SocialRequest request) {
        return new ResponseEntity<>(socialService.updateSocial(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Social")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Social Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Delete Social Failed",
                    content = @Content)})
    @DeleteMapping("/{socialid}")
    public ResponseEntity<SocialResponse> delete(
            @PathVariable(value = "socialid") Long socialId) {
        return new ResponseEntity<>(socialService.deleteSocialPost(socialId), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve All Social")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve All Social Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Retrieve All Social Failed",
                    content = @Content)})
    @GetMapping("/all")
    public ResponseEntity<List<SocialResponse>> retrieveAll() {
        return new ResponseEntity<>(socialService.getSocials(), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve Social by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve Social by ID Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Retrieve Social by ID Failed",
                    content = @Content)})
    @GetMapping("/{socialid}")
    public ResponseEntity<SocialResponse> retrieveById(@PathVariable(name = "socialid") Long socialId) {
        return new ResponseEntity<SocialResponse>(socialService.retrieveById(socialId), HttpStatus.OK);
    }

    @Operation(summary = "Get Social post by community")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Social post by community Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Get Social post by community Failed",
                    content = @Content)})
    @GetMapping("/{communityid}/communityposts")
    public ResponseEntity<List<SocialResponse>> getPostsByCommunity(@PathVariable(name = "communityid") Long communityId) {
        return new ResponseEntity<>(socialService.getPostsByCommunity(communityId), HttpStatus.OK);
    }

    @Operation(summary = "Search Social")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Social Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Search Social Failed",
                    content = @Content)})
    @GetMapping("search")
    public ResponseEntity<List<SocialResponse>> search(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(socialService.search(searchString), HttpStatus.OK);
    }

    @Operation(summary = "Find Social by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Social by ID Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Find Social by ID Failed",
                    content = @Content)})
    @GetMapping("socialdetails/{socialid}")
    public ResponseEntity<SocialPostResponse> findById(@PathVariable(name = "socialid") Long socialPostId) {
        return new ResponseEntity<SocialPostResponse>(socialService.retrieveSocialDetailsById(socialPostId), HttpStatus.OK);
    }

    @Operation(summary = "Find Social by profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Social by profile by ID Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Find Social by profile by ID Failed",
                    content = @Content)})
    @GetMapping("socialposts/{profileid}")
    public ResponseEntity<List<SocialPostResponse>> findByIdProfileId(@PathVariable(name = "profileid") Long profileId) {
        return new ResponseEntity<List<SocialPostResponse>>(socialService.retrievePostsByProfileId(profileId), HttpStatus.OK);
    }
}
