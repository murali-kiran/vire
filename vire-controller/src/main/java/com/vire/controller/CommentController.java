package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.CommentRequest;
import com.vire.model.response.CommentResponse;
import com.vire.service.CommentService;
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
@RequestMapping(VireConstants.COMMENT_REQUEST_PATH_API)
public class CommentController {

    @Autowired
    CommentService commentService;

    @Operation(summary = "Create Comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "comment created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "comment creation failed",
                    content = @Content) })
    @PostMapping("/create")
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CommentRequest request){
        return new ResponseEntity<>(commentService.createComment(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "updated comment successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "comment updation failed",
                    content = @Content) })
    @PutMapping("/update")
    public ResponseEntity<CommentResponse> updateComment(@Valid @RequestBody CommentRequest request){
        return new ResponseEntity<>(commentService.updateComment(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Deletion of comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deletion of comment successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "comment deletion failed",
                    content = @Content) })
    @DeleteMapping("/{commentid}")
    public ResponseEntity<CommentResponse> deleteComment(
            @PathVariable(value = "commentid") Long commentId) {
        return new ResponseEntity<>(commentService.deleteComment(commentId), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve comment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved comment by ID sucessfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "comment retrieval failed",
                    content = @Content) })
    @GetMapping("/{commentid}")
    public ResponseEntity<CommentResponse> retrieveById(@PathVariable(name = "commentid") Long commentId){
        return new ResponseEntity<CommentResponse>(commentService.retrieveById(commentId), HttpStatus.OK);
    }

    @Operation(summary = "Searching of comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "searching comments successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "searching of comments failed",
                    content = @Content) })
    @GetMapping("search")
    public ResponseEntity<List<CommentResponse>> searchComment(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(commentService.searchComments(searchString), HttpStatus.OK);
    }
}
