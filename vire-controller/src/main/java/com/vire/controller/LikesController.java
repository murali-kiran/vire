package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.LikesRequest;
import com.vire.model.response.LikesResponse;
import com.vire.service.LikesService;
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
@RequestMapping(VireConstants.LIKE_REQUEST_PATH_API)
public class LikesController {

    @Autowired
    LikesService likesService;

    @Operation(summary = "Create Likes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create Likes Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LikesResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Create Likes Failed",
                    content = @Content) })
    @PostMapping("/create")
    public ResponseEntity<LikesResponse> createLike(@Valid @RequestBody LikesRequest request){
        return new ResponseEntity<>(likesService.createLike(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Likes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update Likes Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LikesResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Update Likes Failed",
                    content = @Content) })
    @PutMapping("/update")
    public ResponseEntity<LikesResponse> updateLike(@Valid @RequestBody LikesRequest request){
        return new ResponseEntity<>(likesService.updateLike(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Likes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Likes Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LikesResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Delete Likes Failed",
                    content = @Content) })
    @DeleteMapping("/{likeid}")
    public ResponseEntity<LikesResponse> delete(
            @PathVariable(value = "likeid") Long likeid) {
        return new ResponseEntity<>(likesService.deleteLike(likeid), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve Likes by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve Likes by ID Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LikesResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Retrieve Likes by ID Failed",
                    content = @Content) })
    @GetMapping("/{chatid}")
    public ResponseEntity<LikesResponse> retrieveById(@PathVariable Long likeId){
        return new ResponseEntity<LikesResponse>(likesService.retrieveById(likeId), HttpStatus.OK);
    }

    @Operation(summary = "Search Likes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seach Likes Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LikesResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Search Likes Failed",
                    content = @Content) })
    @GetMapping("search")
    public ResponseEntity<List<LikesResponse>> search(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(likesService.searchLikes(searchString), HttpStatus.OK);
    }
}
